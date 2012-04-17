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
	fprintf(stdout, "This is only test: Hello,中国.");
	fprintf(stdout, "</html>");

	fflush(stdout);
	return 0;
}
