X = [3.1, 2.9, 3.8, 3.3, 2.7, 3.0, 2.8, 2.5, 2.6, 2.0, 3.2, 2.4, 2.3, 3.1, 2.1, 3.4]; %bank employees
miux = mean(X);
nx = length(X);

Y = [6.9, 6.4, 4.7, 4.3, 5.1, 6.3, 5.9, 5.4, 5.3, 5.2, 5.1, 5.9, 5.8, 4.9]; #other employees
miuy = mean(Y);
ny = length(Y);

printf('\na)\n');
#N0: the population variances do not differ
#N1: the population variances differ
#since it asks wether the population variances differ, we have a two tailed test

alpha = input('Enter the significance level: '); # we will use 0.05
[h, pval, ci, stats] = vartest2(X, Y, 'alpha', alpha, 'tail', 'both');

#h is a binary, it can be either 1 or 0, depending on wether he hypothesis is rejected or not
if h==0
  printf('The null hypothesis is not rejected.\n The population variances DO NOT differ.\n');
else
  printf('The null hypothesis is rejected.\n The population variances DO differ.\n');
end

#we compute the rejection region
q1 = finv(alpha/2, stats.df1, stats.df2);
q2 = finv(1-alpha/2, stats.df1, stats.df2);
printf('The rejection region is (-inf, %1.6f) U (%1.6f, +inf)\n', q1, q2);
printf('The observed value is: %1.6f\n', stats.fstat);
printf('The p-value from the test statistical is: %1.6f\n', pval);

printf('\nb)\n');
#H0: other employees dispose of more white paper than bank employees
#H1: other employees do not dispose of more white paper than bank employees
#we check wether miuy > miux <=> miux<miuy => left tailed test

alpha = input('Enter the significance level: '); # we will STILL use 0.05
[h, pval, ci, stats] = ttest2(X, Y, 'alpha', alpha, 'tail', 'left', 'vartype', 'unequal');
if h==0
  printf('The null hypothesis is not rejected.\nOther employees DO NOT dispose of more white paper.\n');
else
  printf('The null hypothesis is rejected.\nOther employees DO dispose of more white paper.\n');
end

# we compute the rejection region
q = tinv(alpha, stats.df);
fprintf('The rejection region R is (-infinity, %1.6f)\n', q);
fprintf('The observed value is: %1.6f\n', stats.tstat);
fprintf('The p-value from the test statistic is: %1.6f\n', pval);


