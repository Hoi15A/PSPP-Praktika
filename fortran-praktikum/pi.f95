program  montecarlo
use omp_lib
implicit none
integer*4 :: num, numthreads
character*32  :: arg
if (iargc() < 1) then
  num = 100000000
  numthreads = 2
else
  call getarg(1, arg)
  read( arg, '(i20)') num
  if (iargc() == 2) then
      call getarg(2, arg)
      read( arg, '(i20)') numthreads    
  endif  
endif
call measurepi("calcpi",1000,1)
call measurepi("calcpi",10000 ,1)
call measurepi("calcpi",100000 ,1)
call measurepi("calcpi",num,1)
call measurepi("calcpi_omp1",num,1)
call measurepi("calcpi_omp1",num,numthreads)
call measurepi("calcpi_omp2",num,1)
call measurepi("calcpi_omp2",num,numthreads)
end

subroutine measurepi(algo,num,numthreads)
  implicit none
  real*8, parameter :: exactpi = 4.D0*DATAN(1.D0)
  real*8 :: calcpi, calcpi_omp1, calcpi_omp2, error
  integer*4 :: count_rate, count_max, num, numthreads, t1,t2
  character(*)  :: algo
  real*8 :: pi
  
  call system_clock(t1, count_rate, count_max)
  if (algo == "calcpi") then
    pi = calcpi(num)
  else if (algo == "calcpi_omp1") then
    pi = calcpi_omp1(num,numthreads)
  else if (algo == "calcpi_omp2") then
    pi = calcpi_omp2(num,numthreads)
  endif
  call system_clock(t2, count_rate, count_max)
  error = abs(pi - exactpi)*100
  print '("",a11," ",i10," ",i2," thread pi = ",f12.10," error = ",f4.2,"% time =",i5," ms")',algo,num,numthreads,pi,error,t2-t1
end

function calcpi(num)
  implicit none
  real*8 :: calcpi
  integer*4 :: num
! TO BE DONE 
  integer*4 :: sum_global, i
  real*8 :: x, y, d

  sum_global = 0

  do i = 1, num
    x = rand()
    y = rand()
    d = x**2 + y**2

    if ( d <= 1 ) then
      sum_global = sum_global + 1
    end if
  end do

  calcpi = dble(sum_global) / num * 4.0
end

function calcpi_omp1(num, numthreads)
  implicit none
  real*8 :: calcpi_omp1
  integer*4 :: num, numthreads
! TO BE DONE 

  integer*4 :: sum_global, sum_local, i
  real*8 :: x, y, d

  sum_global = 0

  !$omp parallel private(sum_local,i,x,y,d) num_threads(numthreads)
  sum_local = 0
  do i = 1, num
    x = rand()
    y = rand()
    d = x**2 + y**2

    if ( d <= 1 ) then
      sum_local = sum_local + 1
    end if
  end do

  !$omp critical
  sum_global = sum_global + sum_local
  !$omp end critical
  !$omp end parallel

  calcpi_omp1 = dble(sum_global) / num * 4.0
end 

function calcpi_omp2(num, numthreads)
  implicit none
  real*8 :: calcpi_omp2
  integer*4 :: num,numthreads
! TO BE DONE 
  calcpi_omp2 = 0
end 
