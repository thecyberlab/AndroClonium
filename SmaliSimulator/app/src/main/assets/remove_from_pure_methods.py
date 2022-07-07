a = set()
with open("all_pure_methods.txt", "r") as f:
    for l in f.readlines():
        l = l.strip()
        if not "<init>" in l:
            a.add(l)


with open("all_pure_methods.txt", "w") as f:
    for l in a:
        f.write(l + "\n")