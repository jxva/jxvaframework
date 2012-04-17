#ifndef _JV_FILE_H_INCLUDED_
#define _JV_FILE_H_INCLUDED_

#include <jv_core.h>
#include <jv_string.h>

struct jv_file_s {
	FILE *fd;
	jv_str_t name;
};

#endif /* _JV_FILE_H_INCLUDED_ */
