README

Instructions:
- File IO is optional. there are 3 .csv files in the Resources folder which are example input files.
out.txt is a optional output file. It logs every cell at every generation, so the file can get big
rather quickly, and for large ecosystems/refresh rates, there's a performance hit.
- Most of the other functionality should be pretty self explanatory.

Bug Report:
- The Cell Size slider in the Options menu doesn't work yet.
- If the input file has greater dimensions than the default ecosystem (editable in SeagullApp), the 
"game" won't run. (Cell size 10 px makes them small enough to hold all example input files, but more 
cells = slower performance)

Future Work: (There's plenty to do)
- More options could be added, i.e. variable likelihood of random cell generation (default is 25%)
- Window resizing
- Live ecosystem resizing
- A more aesthetically pleasing way of lighting up cells, perhaps customization options there too
- Centralization of the RandomColor method
- Improve efficiency when writing to a file by putting that process on a separate thread
