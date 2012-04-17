#include <assert.h>
#include <jv_math.h>

void jv_math_test(void) {
	/* printf("%f",jv_radian(90.0)); */

	/* jv_angle */
	assert(jv_angle(3.0/M_PI) - 60.0 <= M_EPSINON);

	/* jv_radian */
	assert(jv_radian(90.0) - M_PI/2 <= M_EPSINON);

	/* jv_sin */
	assert(jv_sin(jv_radian(30.0)) - 0.5 <= M_EPSINON);

	/* jv_cos */
	assert(jv_cos(jv_radian(60.0)) - 0.5 <= M_EPSINON);

	/* jv_tan */
	assert(jv_tan(jv_radian(45.0)) - 1.0 <= M_EPSINON);

	/* jv_cot */
	assert(jv_cot(jv_radian(45.0)) - 1.0 <= M_EPSINON);

	/* jv_sec */
	assert(jv_sec(jv_radian(30.0)) - 2.0 <= M_EPSINON);

	/* jv_csc */
	assert(jv_csc(jv_radian(60.0)) - 2.0 <= M_EPSINON);

	/* jv_exp */
	assert(jv_exp(0) - 1.0 <= M_EPSINON);

	/* jv_tan */
	assert(jv_pow(2,3) - 8.0 <= M_EPSINON);
}
