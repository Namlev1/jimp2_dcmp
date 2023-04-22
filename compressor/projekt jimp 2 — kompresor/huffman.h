#include <stdio.h>
#include <stdlib.h>
#include <string.h>


typedef struct 
{
    char str[256];
    int prob;

} strchar_t;

void read_file(FILE* file);
void huffman_code();
void print_code(FILE* file);
void write_text(FILE* file, FILE* in);