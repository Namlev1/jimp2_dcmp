kompresor: main.o huffman.o 
	$(CC) -o kompresor main.o huffman.o 
main.o : main.c
	$(CC) -c main.c -o main.o

huffman.o : huffman.c
	$(CC) -c huffman.c -o huffman.o




clean:
	rm kompresor *.o
