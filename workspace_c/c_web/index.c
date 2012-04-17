#include <stdio.h>

int main(int argc, char *argv[]) {
	fprintf(stdout, "Content-type:text/html;charset=utf-8\r\n\r\n");

	fprintf(stdout, "<!DOCTYPE>");
	fprintf(stdout, "<html>");
	fprintf(stdout, "<head>");
	fprintf(stdout, "<meta charset=\"utf-8\">");
	fprintf(stdout, "<title>Index</title>");
	fprintf(stdout, "</head>");
	fprintf(stdout, "<body>");
	fprintf(stdout, "<a herf=\"test.cgi\">Test CGI,测试 CGI</a>");
	fprintf(stdout, "</html>");

	fflush(stdout);
	return 0;
}
