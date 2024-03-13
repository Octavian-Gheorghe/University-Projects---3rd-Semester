# here we solve problem 1 a)

alpha = input('Please input the significance level 0 < alpha < 1 alpha = ');

X =[7 7 4 5 9 9 ...
4 12 8 1 8 7 ...
3 13 2 1 17 7 ...
12 5 6 2 1 13 ...
14 10 2 4 9 11 ...
3 5 12 6 10 7
];

n = length(X);

# the null hyphotesis is H0: miu = 8.5 (it is together with miu > 8.5, and the interpretation is:
# the efficiency standard is met)

# the alternative hypothesis is H1: miu < 8.5 (the interpretation is: the efficiency standard is not met)

# this is a left-tailed test for miu
# this is a left-tailed test for the mean when sigma is known - this is the explanation for why we chose this from the cheatsheet

sigma = 5;
m0 = 8.5;

[h,p,ci,zval] = ztest(X,m0,sigma, "alpha", alpha, "tail", "left"); #check at home this thing, ci is kind of useless but it's needed
# in this function so it can be called
# each test must have a significance and a type
# tail must always remain there, don't change "tail"
# "left" can be replaces with "right" or  "both"
# "alpha" needs to be be there to specify what follows is alpha

# the big brains forgot something so now we have to suffer about pval
# we'll modify a function from ztest.m

# ttalpha is the quantile of the probability law, in this case normal distribution
ttalpha = norminv(alpha,0,1);
RR = [-inf ttalpha]; # here we define the rejection region

# the value of h is
printf('The value of h is %d\n', h);
if h == 1
  printf('The null hyphothesis is rejected!\n');
  printf('The data suggests that the standard is not met!\n');
else
  printf('The null hypothesis is not rejected!\n');
  printf('The data suggests that the standard is met!\n');
endif

printf('The rejection region is: (%4.4f, %4.4f)\n', RR);
printf('The value of the test statistic is %4.4f/n', zval);
printf('The pvalue of the test is %4.4f\n',p);

# 1 b test
# 2 a vartest2
# 2 b ttest2
# 1 b H0: miu = 5.5
# H1 : miu > 5.5

