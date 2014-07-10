/*
 * File:   main.cpp
 * Author: mcs10kai
 *
 * Created on May 13, 2011, 9:47 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <omp.h>
#include <string.h>
#include <time.h>
#include <pthread.h>
#include <math.h>
#include <queue>
using namespace std;

struct variable
{
        unsigned int ImageWidth, ImageHeight;
        double MinRe;
        double MaxRe;
        double MinIm;
        double MaxIm;
        double Re_factor;
        double Im_factor;
        unsigned int MaxIteration;
        unsigned int i,j;
        double Z_im,Z_re;
        int **pixel;
        unsigned int NUM_THREADS;
        double Threshsqr;


};

double prog_seqt(variable V1);
double prog_openmp(variable V1);
double prog_pthrd(variable *V1);
double prog_magic(variable V1);


//(4 1000 1000 -2.0 1.0 -1.2 2.0 25500 8 4)

//"${OUTPUT_PATH}"


int main(int argc, char** argv)
{
    struct variable V1;
    V1.ImageHeight = atoi(argv[2]);
    V1.ImageWidth = atoi(argv[3]);
    V1.MinRe = atof(argv[4]);
    V1.MaxRe = atof(argv[5]);
    V1.MinIm = atof(argv[6]);
    V1.MaxIm = atof(argv[7]);
    V1.MaxIteration = atoi(argv[8]);
    V1.NUM_THREADS = atoi(argv[9]);
    V1.Threshsqr = atof(argv[10]);
    V1.Re_factor = (V1.MaxRe-V1.MinRe)/(V1.ImageWidth-1);
    V1.Im_factor = (V1.MaxIm-V1.MinIm)/(V1.ImageHeight-1);
    V1.pixel = (int **)malloc(V1.ImageHeight*sizeof(int*));
    int a=0, x=0,y=0;


    for(a=0; a < V1.ImageHeight; a++)
    {
        V1.pixel[a] = (int*)malloc(V1.ImageWidth*sizeof(int));
    }
    

   //To Initialize pixel array
    for( y=0; y<V1.ImageHeight; ++y)
            {
                for(x=0; x<V1.ImageWidth; ++x)
                {
                     V1.pixel[x][y] =0;
                }
            }

    //converting string to integer
    int i = atoi(argv[1]);

    time_t St,Et;
    double dif1 = 0.0, dif21 = 0.0, dif22 = 0.0, dif3 = 0.0;

    switch(i)
   {
       case 1:
       {
           time(&St);
           prog_seqt(V1);
           time(&Et);
           dif1 = difftime (Et,St);
           printf("It took %.2lf seconds to create the Mandelbrot Fractal by using Sequential Programming\n",dif1);
           break;
       }
       case 2:
       {
           dif21 = prog_openmp(V1);
           printf("It took %.2lf seconds to create the Mandelbrot Fractal by Parallel Programming using OpenMp\n",dif21);
           break;
       }
       case 3:
       {
           dif22 = prog_pthrd(&V1);
           printf("It took %.2lf seconds to create the Mandelbrot Fractal by Parallel Programming using Pthreads\n",dif22);
           break;
       }
        case 4:
        {
           dif3 = prog_magic(V1);
           printf("It took %.2lf seconds to create the Mandelbrot Fractal by Parallel Programming using OpenMp along with MagicBox Algorithm\n",dif3);
           break;
        }
       default:
       {
           printf("Try Again");
       }
   }


}

double prog_openmp(variable V1)
{
    unsigned int x=0,y=0;
    int chunk = 5;
    unsigned int ImageWidth = V1.ImageWidth;
    unsigned int ImageHeight = V1.ImageHeight;
    double MinRe = V1.MinRe;
    double MaxIm = V1.MaxIm;
    double Re_factor = V1.Re_factor;
    double Im_factor = V1.Im_factor;
    int **pixel = V1.pixel;
    unsigned int MaxIteration = V1.MaxIteration;
    double Threshsqr = 2*V1.Threshsqr;
    unsigned int NUM_THREADS = V1.NUM_THREADS;
    //printf("ImageWidth %d,ImageHeight %d,MaxIteration %d,Threshsqr %f,MinRe %f,MaxIm %f,Re_factor %f,Im_factor %f\n", ImageWidth,ImageHeight,MaxIteration,Threshsqr,MinRe,MaxIm,Re_factor,Im_factor);
    time_t St,Et;
    time(&St);
    #pragma omp parallel shared(ImageWidth,ImageHeight,MaxIteration,Threshsqr,MinRe,MaxIm,Re_factor,Im_factor,pixel,chunk) private(x,y) num_threads(NUM_THREADS)
    {
            #pragma omp for schedule(dynamic,chunk)
            for( y=0; y<ImageHeight; ++y)
            {
                double c_im = MaxIm - y*Im_factor;
                for(x=0; x<ImageWidth; ++x)
                {
                    double c_re = MinRe + x*Re_factor;
                    double Z_re = c_re, Z_im = c_im;
                    for(unsigned int n=0; n<MaxIteration; ++n)
                    {
                        double Z_re2 = Z_re*Z_re, Z_im2 = Z_im*Z_im;
                        if(Z_re2 + Z_im2 > (Threshsqr))
                        {
                            pixel[y][x]=n;
                            if(pixel[y][x]==10)
                             {
                              pixel[y][x]=11;
                             }
                            break;
                        }
                        Z_im = 2*Z_re*Z_im + c_im;
                        Z_re = Z_re2 - Z_im2 + c_re;
                    }
                }
            }
   }
   time(&Et);
   double dif = difftime (Et,St);
   FILE *fp = fopen("first.ppm","w+");
    if(fp!=NULL)
    {
        fprintf(fp,"P6\n%d %d\n255\n",ImageWidth,ImageHeight);
        for( y=0; y<ImageHeight; ++y)
            {

               for(x=0; x<ImageWidth; ++x)
                {
                    putc(pixel[y][x],fp);
                    putc(0,fp);
                    putc(0,fp);
                }
            }
        fclose(fp);
    }
    return dif;
}

double prog_seqt(variable V1)
{
    int x=0,y=0;
    int R=0,G=0,B=0;
    FILE *fp = fopen("first.ppm","w+");
    if(fp!=NULL)
    {
        fprintf(fp,"P6\n%d %d\n255\n",V1.ImageWidth,V1.ImageHeight);
           for( y=0; y<V1.ImageHeight; ++y)
            {
                double c_im = V1.MaxIm - y*V1.Im_factor;
                for(x=0; x<V1.ImageWidth; ++x)
                {
                    double c_re = V1.MinRe + x*V1.Re_factor;
                    double Z_re = c_re, Z_im = c_im;
                    bool isInside = true;
                    R=0;
                    for(unsigned int n=0; n<V1.MaxIteration; ++n)
                    {
                        double Z_re2 = Z_re*Z_re, Z_im2 = Z_im*Z_im;
                        if(Z_re2 + Z_im2 > 4)
                        {
                            isInside = false;
                            R=n;
                            if(R==10)
                             {
                                R=11;
                             }
                            break;
                        }
                        Z_im = 2*Z_re*Z_im + c_im;
                        Z_re = Z_re2 - Z_im2 + c_re;
                    }

                   putc(R,fp);
                    putc(G,fp);
                    putc(B,fp);

                 }
            }


        fclose(fp);
    }
     return 0;
}

unsigned int CalcColor(unsigned int y,unsigned int x,unsigned int MaxIteration,double Threshsqr,double MaxIm,double MinRe,double Im_factor,double Re_factor)
{

    double Cim = MaxIm - y*Im_factor;
    double Cre = MinRe + x*Re_factor;
    double Zre = Cre, Zim = Cim;
    unsigned int cd = 0;
    for (unsigned int n=1; n < MaxIteration; ++n)
    {
        double Zre2 = Zre*Zre, Zim2 = Zim*Zim;
        if(Zre2 +Zim2 > Threshsqr)
        {
            cd = n;
            break;
        }
        Zim = 2*Zre*Zim + Cim;
        Zre = Zre2 - Zim2 + Cre;
    }
    return cd;
}


struct variable2{
    int t;
    variable *V1;
};


pthread_mutex_t currentYpixel_mutex;
int currentYpixel = 0;

void *CalPixMandel(void *d)
{
    struct variable2 *V2 = ( variable2 *)d;
    int y = -1, x = 0;
    pthread_mutex_lock(&currentYpixel_mutex);
    if(currentYpixel <  V2->V1->ImageHeight)
    {
        y = currentYpixel;
        currentYpixel++;
    }
    pthread_mutex_unlock(&currentYpixel_mutex);
    while(y >= 0)
    {
        for(x=0; x< V2->V1->ImageWidth; ++x)
        {
           V2->V1->pixel[y][x] = CalcColor(y,x,V2->V1->MaxIteration,V2->V1->Threshsqr,V2->V1->MaxIm,V2->V1->MinRe,V2->V1->Im_factor,V2->V1->Re_factor);
        }

        y = -1;

        pthread_mutex_lock(&currentYpixel_mutex);
        if(currentYpixel <  V2->V1->ImageHeight)
        {
            y = currentYpixel;
            currentYpixel++;
        }
        pthread_mutex_unlock(&currentYpixel_mutex);
    }
    pthread_exit(NULL);
}

double prog_pthrd(variable *V1)
{

    int y=0, x=0, t=0;
    struct variable2 V2[V1->NUM_THREADS];
    pthread_t threads[V1->NUM_THREADS];
    pthread_mutex_init(&currentYpixel_mutex,NULL);
    time_t St,Et;
    time(&St);

    for(t=0; t<V1->NUM_THREADS; t++)
    {
        V2[t].t = t;
        V2[t].V1 = V1;
        pthread_create(&threads[t],NULL, CalPixMandel,(void *)&V2[t]);
    }

    for(t=0; t<V1->NUM_THREADS; t++)
    {
        pthread_join(threads[t],NULL);
    }

    pthread_mutex_destroy(&currentYpixel_mutex);
    time(&Et);
    double dif = difftime (Et,St);

    FILE *pFile = fopen("first.ppm","w+");
    if (pFile != NULL)
    {
       fprintf(pFile,"P6\n%d %d\n255\n",V1->ImageWidth,V1->ImageHeight);
       unsigned int color1 = 0, color2 = 0, color3 = 0;
       for(y=0; y<V1->ImageHeight; ++y)
       {
           for(x=0; x<V1->ImageWidth; ++x)
           {
               color1 = (V1->pixel[y][x]*10)%155;
               color2 = 255-((V1->pixel[y][x]*10)%155);
               color3 = ((V1->pixel[y][x]*10)-150)%155;
               if(color1 ==10) color1 = 11;
               if(color2 ==10) color2 = 11;
               if(color3 ==10) color3 = 11;
               putc(color1, pFile);
               putc(color2, pFile);
               putc(color3, pFile);
           }
       }
       fclose(pFile);
    }

    return dif;
}



struct corner
{
    int p[4];
};

queue<corner*> pQ;


void magicBox(unsigned int Sy,unsigned int Sx,unsigned int Ey,unsigned int Ex,unsigned int MaxIteration,double Threshsqr,double MaxIm,double MinRe,double Im_factor,double Re_factor,variable *V1, bool force = false)
{

    bool split = false;
    if(force) split = true;
    int boxS = 4;
    unsigned int C1 =0;

    C1 = CalcColor(Sy,Sx,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor);

    for(unsigned int i=Sy; i<Ey; i++)
    {
        if(C1!= CalcColor(i,Sx,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor))
        {
            split = true;
            break;
        }
        if(C1!= CalcColor(i,Ex,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor))
        {
            split = true;
            break;
        }
    }
    for(unsigned int i=Sx; i<Ex; i++)
    {
        if(C1!= CalcColor(Sy,i,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor))
        {
            split = true;
            break;
        }
        if(C1!= CalcColor(Ey,i,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor))
        {
            split = true;
            break;
        }
    }
    if(split == false)
    {
         for(unsigned int i=Sy; i<Ey; i++)
            for(unsigned int j=Sx; j<Ex; j++)
            {
                V1->pixel[i][j] = C1;
            }
    }else
    {
        int lenY = Ey - Sy;
        int lenX = Ex - Sx;

        if(lenY > boxS && lenX > boxS)
        {
            unsigned int MPY = Sy + (int)((lenY)/2);
            unsigned int MPX = Sx + (int)((lenX)/2);

            #pragma omp critical
            {
                struct corner *ct = new corner;
                ct->p[0] = Sy; ct->p[1] = Sx; ct->p[2] = MPY; ct->p[3] = MPX;
                pQ.push(ct);

                ct = new corner;
                ct->p[0] = Sy; ct->p[1] = MPX; ct->p[2] = MPY; ct->p[3] = Ex;
                pQ.push(ct);

                ct = new corner;
                ct->p[0] = MPY; ct->p[1] = Sx; ct->p[2] = Ey; ct->p[3] = MPX;
                pQ.push(ct);

                ct = new corner;
                ct->p[0] = MPY; ct->p[1] = MPX; ct->p[2] = Ey; ct->p[3] = Ex;
                pQ.push(ct);

            }
        }else
        {
            for(unsigned int i = Sy; i < Ey; i++)
                for(unsigned int j = Sx; j < Ex; j++)
                {
                    V1->pixel[i][j] =  CalcColor(i,j,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor);
                }
        }
    }

}

double prog_magic(variable V1)
{
    int y=0,x=0,count = 0;
    unsigned int ImageHeight = V1.ImageHeight;
    unsigned int ImageWidth = V1.ImageWidth;
    unsigned int MaxIteration = V1.MaxIteration;
    double Threshsqr = V1.Threshsqr;
    double MaxIm = V1.MaxIm;
    double MinRe = V1.MinRe;
    double Im_factor = V1.Im_factor;
    double Re_factor = V1.Re_factor;
    unsigned int NUM_THREADS = V1.NUM_THREADS;
    struct corner *ct = NULL;
    time_t St,Et;
    time(&St);
    printf("ImageWidth %d,ImageHeight %d,MaxIteration %d,Threshsqr %f,MinRe %f,MaxIm %f,Re_factor %f,Im_factor %f\n", ImageWidth,ImageHeight,MaxIteration,Threshsqr,MinRe,MaxIm,Re_factor,Im_factor);

    magicBox(0,0,ImageHeight,ImageWidth,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor,&V1, true);

    #pragma omp parallel shared(MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor, pQ) private(ct) num_threads(NUM_THREADS)
    {
        int size = pQ.size();
        while(size > 0)
        {
            int Sy = 0, Sx = 0, Ey = 0, Ex =0;
            #pragma omp critical
            {
                ct =pQ.front();
                if(ct)
                {
                    pQ.pop();
                    Sy = ct->p[0], Sx = ct->p[1], Ey = ct->p[2], Ex = ct->p[3];
                    delete ct;
                    ct = NULL;
                    count++;
                }
            }
            magicBox(Sy,Sx,Ey,Ex,MaxIteration,Threshsqr,MaxIm,MinRe,Im_factor,Re_factor,&V1);
            size = pQ.size();
        }
    }
    time(&Et);
    double dif = difftime (Et,St);

    FILE *pFile = fopen("first.ppm","w+");
    if (pFile != NULL)
    {
       fprintf(pFile,"P6\n%d %d\n255\n",ImageWidth,ImageHeight);
       unsigned int color1 = 0, color2 = 0, color3 = 0;
       for(y=0; y<ImageHeight; ++y)
       {
           for(x=0; x<ImageWidth; ++x)
           {
               color1 = (V1.pixel[y][x]*10)%155;
               color2 = 255-((V1.pixel[y][x]*10)%155);
               color3 = ((V1.pixel[y][x]*10)-150)%155;
               if(color1 ==10) color1 = 11;
               if(color2 ==10) color2 = 11;
               if(color3 ==10) color3 = 11;
               putc(color1, pFile);
               putc(color2, pFile);
               putc(color3, pFile);
           }
       }
       fclose(pFile);
    }
    return dif;
}
