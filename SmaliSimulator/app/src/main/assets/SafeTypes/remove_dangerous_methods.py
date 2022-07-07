a = set()
with open("InstancePartialSafeClasses.txt", "r") as f:
    for l in f.readlines():
        l = l.strip()
        if not "connect(" in l:
            a.add(l)


with open("InstancePartialSafeClasses.txt", "w") as f:
    for l in a:
        f.write(l + "\n")