#ifndef _JV_HASH_H_INCLUDED_
#define _JV_HASH_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>

typedef struct {
	size_t len;
	u_char *data;
} jv_str_t;

#define jv_string(str)              { sizeof(str) - 1, (u_char *) str }
#define jv_null_string              { 0, NULL }
#define jv_str_set(str, text)                                               \
    (str)->len = sizeof(text) - 1; (str)->data = (u_char *) text

#define jv_str_null(str)            (str)->len = 0; (str)->data = NULL

typedef struct jv_pair_s jv_pair_t;
typedef struct jv_hash_s jv_hash_t;
typedef struct jv_slot_s jv_slot_t;
typedef struct jv_entry_s jv_entry_t;

/*
 typedef struct jv_chunk_s       jv_chunk_s;
 typedef struct jv_bucket_s       jv_bucket_s;
 */

struct jv_pair_s {
	jv_str_t key;
	jv_str_t value;
};

struct jv_hash_t {
	jv_uint_t max; /* 初始化数组的最大索引 */
	jv_pool_t *pool;
};

struct jv_slot_t {
	jv_uint_t size;
	jv_entry_t *head;
};

struct jv_entry_s {
	jv_pair_t entry;
	jv_uint_t hash;
	jv_entry_t *next;
};

jv_hash_t *jv_hash_create(jv_pool_t *pool, jv_uint_t max);
jv_int_t jv_hash_put(jv_hash_t *hash, jv_pair_t *pair);
jv_entry_t *jv_hash_get(jv_hash_t *hash, jv_str_t key);
jv_uint_t jv_hash_size(jv_hash_t *hash);
jv_int_t jv_hash_remove(jv_hash_t *hash, jv_str_t key);
jv_int_t jv_hash_destroy(jv_hash_t *hash);

#endif /* _JV_HASH_H_INCLUDED_ */
