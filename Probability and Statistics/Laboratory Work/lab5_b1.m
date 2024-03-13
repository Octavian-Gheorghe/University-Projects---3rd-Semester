# here we solve exercise B1
X=[7 7 4 5 9 9 ...
  4 12 8 1 8 7 ...
  3 13 2 1 17 7 ...
  12 5 6 2 1 13 ...
  14 10 2 4 9 11 ...
  3 5 12 6 10 7]; # this way we write it as a 1x36 vector

# a)
l=length(X); # l will contain the length of X, l is n
oneminusalpha=input('Input the confidence level = '); # we'll use 0.95
alpha=1-oneminusalpha;
sigma=5;
m1a=mean(X)-sigma/sqrt(l)*norminv(1-alpha/2,0,1);
m2a=mean(X)-sigma/sqrt(l)*norminv(alpha/2,0,1);
printf('The confidence interval for the theoretical mean, when sigma is known is: ');
printf('%4.3f,%4.3f\n',m1a,m2a);

# b)
m1b=mean(X)-std(X)/sqrt(l)*tinv(1-alpha/2,l-1);
m2b=mean(X)-std(X)/sqrt(l)*tinv(alpha/2,l-1);
printf('The confidence interval for the theoretical mean, when sigma is unknown is: ');
printf('%4.3f,%4.3f\n',m1b,m2b);

# c)
m1c=(l-1)*var(X)/chi2inv(1-alpha/2,l-1);
m2c=(l-1)*var(X)/chi2inv(alpha/2,l-1);
printf('The confidence interval for the theoretical variance is: ');
printf('%4.3f,%4.3f\n',m1c,m2c);
s1=sqrt(m1c);
s2=sqrt(m2c);
printf('The confidence interval for the theoretical standard deviation is: ');
printf('%4.3f,%4.3f\n',s1,s2);
