# we solve exercise B 2
X1=[22.4 21.7 ...
  24.5 23.4 ...
  21.6 23.3 ...
  22.4 21.6 ...
  24.8 20.0]; # cars with premium
X2=[17.7 14.8 ...
  19.6 19.6 ...
  12.1 14.8 ...
  15.4 12.6 ...
  14.0 12.2]; # cars with standard

n1=length(X1);
n2=length(X2);
oneminusalpha=input('Input the confidence level = '); # we'll use 0.95
alpha=1-oneminusalpha;
# a)
sp=sqrt(((n1-1)*var(X1)+(n2-1)*var(X2))/(n1+n2-2));
m1a=mean(X1)-mean(X2)-tinv((1-alpha/2)*sp,n1+n2-2)*sqrt(1/n1+1/n2);
m2a=mean(X1)-mean(X2);#+tinv((1-alpha/2)*sp,n1+n2-2)*sqrt(1/n1+1/n2);
printf('The confidence interval for the difference of true means when sigma1=sigma2 unknown is: ');
printf('%4.3f,%4.3f\n',m1a,m2a);

# c)
m1c=1/finv(1-alpha/2,n1-1,n2-1)*var(X1)/var(X2);
m2c=1/finv(alpha/2,n1-1,n2-1)*var(X1)/var(X2);
printf('The confidence interval for the ratio of the variances is: ');
printf('%4.3f,%4.3f\n',m1c,m2c);
