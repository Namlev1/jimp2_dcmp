#include "huffman.h"
int compare(const void *a, const void *b);
int search_min();
void make_code(char* first, char* second);
void remove_tab(int fi, int si);
int search_tab(int min, int ind);
void code_reverse();
void write_bits_to_file(FILE* in, const char* binary_string);


#define N 256
unsigned int counters[N] = {0};
char code[N][N] = {""};
size_t length;
strchar_t strchar[N];
unsigned int char_count = 0;
void read_file(FILE* file)
{
    int c = 0;
    
    while((c = fgetc(file)) != EOF)
    {
        counters[c]++;
    }
    for(int i = 0; i < N; i++)
    {
        if(counters[i] != 0)
        {
            strchar[char_count].prob = counters[i];
            strchar[char_count].str[0] = i;
            char_count++;
        }
    }
}


void huffman_code()
{
    int n = char_count - 1;
    while(n--)
    {
        int fi = search_tab(search_min(), -1);
        int si = search_tab(search_min(), fi);
        make_code(strchar[fi].str, strchar[si].str);
        remove_tab(fi, si);
        
    }
}

int search_min()
{
    qsort(counters, N, sizeof(int), compare);
    int i = 0;
    while(counters[i] == 0)
    {
        i++;
    }
    
    int temp = counters[i];
    counters[i] = 0;
    return temp;
}

int search_tab(int min, int ind)
{
    for(int i = 0; i < char_count; i++)
    {
        if((min == strchar[i].prob) && (i != ind))
        {
            return i;
        }
    }
    return 0;
}

void make_code(char* first, char* second)
{
    for(int i = 0; i < strlen(first); i++)
    {
        strcat(code[first[i]], "0"); 
    }
    for(int i = 0; i < strlen(second); i++)
    {
        strcat(code[second[i]], "1"); 
    }
   
}

void remove_tab(int fi, int si)
{
    strcat(strchar[fi].str, strchar[si].str);
    strchar[fi].prob += strchar[si].prob;
    strchar[si].prob = 0;
    counters[0] = strchar[fi].prob;
}


void code_reverse()
{
    for(int i = 0; i < N; i++)
    {
        for(int j = 0; j < strlen(code[i]) / 2; j++)
        {
            char temp = code[i][j];
            code[i][j] = code[i][strlen(code[i]) - 1 - j];
            code[i][strlen(code[i]) - 1 - j] = temp;
        }
    }
}

int compare(const void *a, const void *b) {
    int *x = (int *) a;
    int *y = (int *) b;
    return (*x - *y);
}

void print_code(FILE* file)
{
    
    fprintf(file, "%d %d\n", (int)(length / 8), (int)(length % 8));
    for(int i = 0; i < N; i++)
    {
        if(strcmp(code[i], ""))
        {
            fprintf(file, "%c %s\n", i, code[i]);
        }
       
    }
}

void write_text(FILE* file, FILE* in)
{
    code_reverse();
    int c;
    int n = N * 100;
    int i = 0;
    char* str = malloc(n);
    strcpy(str,"");
    fseek(file, 0, SEEK_SET);
    while((c = fgetc(file)) != EOF)
    {
        i+= strlen(code[c]);
        if(n < i)
        {
            str = realloc(str, N * 100);
            n += N * 100;
        }
        strcat(str, code[c]);
    }
    write_bits_to_file(in, str);
    free(str);
}

void write_bits_to_file(FILE* file, const char* binary_string) {
    
   
    length = strlen(binary_string);
    size_t cbits = length % 8;
    char str_one_bits[8] = "00000000";

   
    for (size_t i = 0; i < length - cbits; i += 8) {
        char byte_str[9]; 
        strncpy(byte_str, &binary_string[i], 8);
        byte_str[8] = '\0';
        int byte = strtol(byte_str, NULL, 2);
        fputc(byte, file);
    }
    for (int i = length - cbits, j = 0; i < length; i++, j++)
    {
        str_one_bits[j] = binary_string[i];
    }
    if(cbits)
    {
        int byte = strtol(str_one_bits, NULL, 2);
        fputc(byte, file);
    }
    
}




