package in.praj.demo;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;

public class BasicApp {
    public static void main(String[] args) {
        var lib = LibraryLoader.create(LibMinimal.class).load("minimal");
        var runtime = Runtime.getRuntime(lib);

        System.out.println("Meaning of life = " + lib.get_integer());

        int first = -559087616, second = 48879;
        System.out.printf("%d + %d = %x\n", first, second, lib.get_sum(first, second));

        System.out.println("Received = " + lib.get_string());

        var builder = new StringBuilder("WXYZ");
        lib.mutate_string(builder);
        System.out.println("Reversed = " + builder);

        var smallStruct = lib.get_small_struct();
        System.out.println(smallStruct);

        var largeStruct = new LibMinimal.LargeStruct(runtime);
        largeStruct.small.set(smallStruct);
        lib.fill_large_struct(largeStruct);
        System.out.printf("Large struct data = %s, %d, %f\n",
                largeStruct.name.get(), smallStruct.index.get(), smallStruct.value.get());

        var letterUnion = new LibMinimal.LetterUnion(runtime);
        lib.fill_letter_union(letterUnion);
        System.out.printf("Letter returned = %c\n", letterUnion.l.get());

        System.out.println("Weather today = " + lib.get_weather());

        var opaque = lib.get_opaque_pointer();
        System.out.println("Extraction result = " + lib.some_opaque_extraction(opaque));

        System.out.printf("Square of %d = %d\n", 7, lib.apply_unary_function(7, n -> n * n));
    }
}
