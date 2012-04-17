#ifndef _JV_MATH_H_INCLUDED_
#define _JV_MATH_H_INCLUDED_

#include <jv_core.h>

#ifndef M_PI
#define M_PI		3.14159265358979323846
#endif

#ifndef M_E
#define M_E		    2.7182818284590452354
#endif

#ifndef M_EPSINON
#define M_EPSINON	0.000001
#endif

/*
 B
 /|
 / |
 (chypotenuse)  c  /  |
 斜边         /   | a (opposite)对边
 /    |
 /_____|
 A   b  C
 (abjacent) 邻边


 */

/*
 angle & radian convert
 function            domain      range
 r is radian
 a is angle(°)
 */

#define jv_angle(r)     (((r)*180.0)/M_PI)
#define jv_radian(a)    (((a)*M_PI)/180.0)

/*±
 trigonometric functions
 function            domain                      range
 sin(A) = a/c        (-∞,+∞)                     [-1,1]
 cos(A) = b/c        (-∞,+∞)                     [-1,1]
 tan(A) = a/b        (-π/2+kπ,π/2+kπ)(k∈z})    (-∞,+∞)
 cot(A) = b/a        (kπ,(k+1)π)(k∈z})         (-∞,+∞)
 sec(A) = c/b
 csc(A) = c/a
 */

#define jv_sin(r)       sin((r))
#define jv_cos(r)       cos((r))
#define jv_tan(r)       (sin((r))/cos((r)))
#define jv_cot(r)       (cos((r))/sin((r)))
#define jv_sec(r)       (1.0/cos((r)))
#define jv_csc(r)       (1.0/sin((r)))

/*
 inverse trigonometric function
 function            domain      range
 arcsin(x)           [-1,1]      [-π/2,π/2]
 arccos(x)           [-1,1]      [0,π]
 arctan(x)           (-∞,+∞)     (-π/2,π/2)
 arccot(x)           (-∞,+∞)     (0,π)
 arcsec(x)
 arccsc(x)
 */

#define jv_arcsin(x)    asin((x))
#define jv_arccos(x)    acos((x))
#define jv_arctan(x)    atan((x))
#define jv_arccot(x)    0
#define jv_arcsec(x)    0
#define jv_arccsc(x)    0

/*
 hyperbolic functions
 function                                                    domain      range
 sh(x)  = sinh(x) = (e^x - e^-x)/2                           (-∞,+∞)     (-∞,+∞)
 ch(x)  = cosh(x) = (e^x + e^-x)/2                           (-∞,+∞)     (-∞,+∞)
 th(x)  = tanh(x) = sh(x)/ch(x) = (e^x - e^-x)/(e^x + e^-x)  (-∞,+∞)     (-1,1)
 cth(x) = coth(x) = ch(x)/sh(x) = (e^x + e^-x)/(e^x - e^-x)
 sech(x)= 1/ch(x) = 2/(e^x + e^-x)
 csch(x)= 1/sh(x) = 2/(e^x - e^-x)
 */

#define jv_sh(x)        sinh((x))
#define jv_ch(x)        cosh((x))
#define jv_th(x)        tanh((x))
#define jv_cth(x)       (1.0/tanh((x)))
#define jv_sech(x)      0
#define jv_csch(x)      0

/*
 inverse hyerbolic function
 function                                domain      range
 arsh(x)   = ln(x+((x^2+1)^-(1/2)))      (-∞,+∞)     (-∞,+∞)
 arch(x)   = ln(x+((x^2-1)^-(1/2)))      [1,+∞)      [0,+∞)
 arth(x)   = ln((1+x)/(1-x))/2           (-1,1)      (-∞,+∞)
 arsech(x) =
 arcsch(x) =
 */
#define jv_arsh(x)      0
#define jv_arch(x)      0
#define jv_arth(x)      0
#define jv_arcth(x)     0
#define jv_arsech(x)    0
#define jv_arcsch(x)    0

/*
 e^x
 domain & range is (-∞,-∞)
 */
#define jv_exp(x)       exp((x))

/*
 x^y
 if x=0&&y<=0 or x<0&&y not is int
 will issue domain error: EDOM
 */
#define jv_pow(x,y)     pow((x),(y))

/*
 x^(1/2),
 domain & range is [0,+∞)
 */
#define jv_sqrt(x)      sqrt((x))

/* |x| */
#define jv_fabs(x)      fabs((x))

/* 「x   returns the smallest integer value greater than or equal to x. */
#define jv_ceil(x)      ceil((x))

/* x」   returns the largest integer value less than or equal to x. */
#define jv_floor(x)     floor((x))

/* base-10, x>0 */
#define jv_log(x)       log10((x))

/* base-e, x>0 */
#define jv_ln(x)        log((x))

/* base-2, x>0 */
#define jv_lg(x)        (log((x))/log((2)))

/* x,y must be integer */
#define jv_mod(x,y)     ((x)%(y))

/*
 x,y must be double, and result's sign equlas x,if y is zero
 the results will be dependent on the concrete implementation.
 */
#define jv_fmod(x,y)     fmod((x),(y))

/*
 other functions
 atan2(y,x)
 ldexp(x,n)
 frexp(x,int *exp);
 modf(x,double *ip);
 */

#endif /* _JV_MATH_H_INCLUDED_ */
