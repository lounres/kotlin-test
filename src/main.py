from random import choice


digits = list(map(str, [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]))

with open("input.txt", "w", encoding="utf8") as outfile:
    for _ in range(10 + 2 * 5000):
        outfile.write(choice(digits))
