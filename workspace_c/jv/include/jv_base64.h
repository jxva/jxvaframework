#ifndef _JV_BASE64_H_INCLUDED_
#define _JV_BASE64_H_INCLUDED_

#include <jv_core.h>
#include <jv_string.h>

u_char *jv_base64_encode(u_char *dst, const jv_str_t *src);

u_char *jv_base64_decode(u_char *dst, const jv_str_t *src);

u_char *jv_base64_decode_url(u_char *dst, const jv_str_t *src);

#endif /* _JV_BASE64_H_INCLUDED_ */
