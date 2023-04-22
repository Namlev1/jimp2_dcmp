#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include <unistd.h>

#include "huffman.h"



int main(int argc, char* argv[])
{
    if (argc != 5)
    {
        printf("usage: ./kompresor -i <input file> -o <output file>\n");
        return 1;
    }
    int c = 0;
    
    char* input_file = NULL;
    char* output_file = NULL;

    while ((c = getopt (argc, argv, "i:o:")) != -1)
    {
        switch (c)
        {
            case 'i':
                input_file = optarg;
                break;
            case 'o':
                output_file = optarg;
                break;    
        }
    }
    

    if(output_file == NULL)
    {
        printf("Podaj plik wyjsciowy\n");
        return 1;
    }
    if(input_file == NULL)
    {
        printf("Podaj plik wejsciowy\n");
        return 1;
    }
    FILE* input = fopen(input_file, "r");
    if(input == NULL)
    {
        printf("Plik %s nie zostal otwarty\n", input_file);
    }
    FILE* output = fopen(output_file, "wb");
    if(output == NULL)
    {
        printf("Plik %s nie zostal otwarty\n", output_file);
    }
    FILE* code = fopen("code.txt", "w");
    read_file(input);
    huffman_code();
    write_text(input, output);
    print_code(code);


    fclose(input);
    fclose(output);
    fclose(code);
    return 0;
}