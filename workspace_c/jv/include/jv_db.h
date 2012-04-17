#ifndef _JV_DATABASE_POOL_H_INCLUDED_
#define _JV_DATABASE_POOL_H_INCLUDED_

#include <jv_core.h>

typedef struct jv_dpool_s jv_dpool_t;

struct jv_dpool_s {
	jv_uint_t min;
	jv_uint_t max;

};

jv_dpool_t *jv_dpool_create();

void *jv_dpool_destroy(jv_dpool_t *dpool);

#endif /* _JV_DATABASE_POOL_H_INCLUDED_ */
