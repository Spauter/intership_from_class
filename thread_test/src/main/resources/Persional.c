#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define MAX 10 

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
struct complex{
    int real;
    int imag;
	};

struct complex multiply(struct complex x,	struct complex y){
	struct complex product;
	product.real=x.real*y.real-x.imag*y.imag;
	product.imag=x.imag*y.real-x.real*y.imag;
	return product;
}

char *str_cat( char *s, char *t){
	int len, i, j=0;
	len= strlen(s);
	for(i=0;i<MAX;i++){
		s[j+len]=t[i];
		j++;
	}
	return s;
}

void questionOne(){
	system("cls");
	printf("****第一题*****\n\n\n");
	printf("***********\n");
	printf("  Helcome\n");
	printf("***********\n");
	system("pause"); 
}

void questionTwo(){
	system("cls");
	printf("****第二题*****\n");
	int mun,a,b,c;
	char choice='y'; 
	do{
		printf("请输入一个三位数：");
		scanf("%d",	&mun);
		if(abs(mun-550)>450)
			printf("Invalid Value\n");
		else{
			a=mun/100;
			b=mun/10%10;
			c=mun%10;
			if(mun== a*a*a+b*b*b+c*c*c)
				printf("Yes\n");
			else{
				printf("No\n");
			}
		}
		fflush(stdin);	
		printf("Continue?(y for yes)");
		scanf("%c" ,&choice); 
	}
	while(choice== 'y');
	system("pause");
}

void questionThree(){
	system("cls") ;
	printf("****第三题*****\n");		 	
	int i,j,N,n;
	printf("请输入M和N:\n") ;
  	scanf("%d",&N);
  	scanf("%d",&n);
	for(i=1;i<=N;i++){
		for(j=1;j<=n;j++){
			if(j<=i){
			printf("%d*%d=%d\t",j,i,i*j);
			}
		}	
		printf("\n");
	}
	system("pause");
}

void questionFour(){
	system("cls");
	printf("****第四题*****\n");
	int a[7][7], i, j, m, n;
	scanf("%d %d", &m, &n);
	for (i = 0; i < m; i++){
		for (j = 0; j < n; j++){
			scanf("%d", &a[i][j]);
		}
	}
	for (i = 0; i < m; i++){
		int sum = 0;
		for (j = 0; j < n; j++){
			sum += a[i][j];
		}
		printf("%d\n", sum);
	}
	system("pause");
}

void qustionFive(){
	system("cls");
	int i;
	int sum=0;
	int a;
	int N;
	printf("****第五题*****\n");
	printf("请输入N:"); 
	scanf("%d",	&N);
	for(i=1;i<=N;i++){
		a=pow(2.0,1.0*i);
		sum=sum+a;
	}
	printf("result=%d", sum);
	system("pause");
}

void questionSix(){
	system("cls");
	printf("****第六题*****\n");
	printf("请输入两串字符：\n");
    char *p;
    char str1[MAX+MAX] = {'\0'}, str2[MAX] = {'\0'};
    scanf("%s%s", str1, str2);
    p = str_cat(str1, str2);
    printf("%s\n%s\n", p, str1);
	system("pause");
} 

void questionSeven(){
	system("cls");
	printf("****第七题*****\n");
	struct complex product, x, y;
	printf("a+bi,c+di(a,b,c,d以空格或者换行分开)：\n");
    scanf("%d%d%d%d", &x.real, &x.imag, &y.real, &y.imag);
    product = multiply(x, y);
    printf("(%d+%di) * (%d+%di) = %d + %di\n", 
            x.real, x.imag, y.real, y.imag, product.real, product.imag);
    system("pause");
}

int main(int argc, char *argv[]) {
	char choice='y';
	do{
		system("cls");
		int i;
		printf("请输入需要查看的题目：") ;
		scanf("%d", &i) ;
		switch(i){
			case 1:
				questionOne();
				break;
			case 2:
				questionTwo();
				break;
			case 3:
				questionThree();
				break;
			case 4:
				questionFour();
				break;
			case 5:
				qustionFive();
				break;
			case 6:
				questionSix();
				break;	
			case 7:	
				questionSeven();
				break;
			default:
				printf("No such choice!\n");
				break; 
			}
		fflush(stdin);
		printf("查看其它题目?(y for yes)");
		scanf("%c" ,&choice); 
		
	}
	while(choice== 'y');
	system("pause");
	return 0;
}
