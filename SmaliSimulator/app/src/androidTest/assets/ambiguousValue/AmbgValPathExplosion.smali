.class public LAmbgValPathExplosion;
.super Ljava/lang/Object;

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# simple for loop
.method public static simpleLoop(I)V
    .locals 3

    const/4 v0, 0x0

    :goto_0
    if-ge v0, p0, :cond_0

    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v2, "*"

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method

# non nested for loops
.method public static nonNestedLoops(I)V
    .locals 4

    add-int/lit8 v0, p0, 0x1

    :goto_0
    if-ge v0, p0, :cond_0

    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v2, "1"

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_1
    if-ge v1, p0, :cond_1

    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v3, "2"

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    add-int/lit8 v1, v1, 0x3

    goto :goto_1

    :cond_1
    const/4 v0, 0x1

    :goto_2
    rem-int/lit8 v1, v0, 0xd

    if-nez v1, :cond_2

    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v2, "C"

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    add-int/lit8 v0, v0, 0x1

    goto :goto_2

    :cond_2
    return-void
.end method


# two nested lopps
.method public static nestedLoops(I)V
    .locals 4

    const/4 v0, 0x0

    :goto_0
    if-ge v0, p0, :cond_1

    const/4 v1, 0x0

    :goto_1
    add-int v2, p0, v0

    if-ge v1, v2, :cond_0

    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v3, "$$"

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    add-int/lit8 v1, v1, 0x1

    goto :goto_1

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

# fibonacci recursion
.method public static fibonacci(I)I
    .locals 2

    const/4 v0, 0x1

    if-gt p0, v0, :cond_0

    return v0

    :cond_0
    add-int/lit8 v0, p0, -0x1

    invoke-static {v0}, LAmbgValPathExplosion;->fibonacci(I)I

    move-result v0

    add-int/lit8 v1, p0, -0x2

    invoke-static {v1}, LAmbgValPathExplosion;->fibonacci(I)I

    move-result v1

    add-int/2addr v0, v1

    return v0
.end method

# two functions calling each other recursively
.method public static isEven(I)Z
    .locals 2

    const/4 v0, 0x1

    if-nez p0, :cond_0

    return v0

    :cond_0
    if-ne p0, v0, :cond_1

    const/4 v0, 0x0

    return v0

    :cond_1
    if-lez p0, :cond_2

    add-int/lit8 v1, p0, -0x1

    invoke-static {v1}, LAmbgValPathExplosion;->isOdd(I)Z

    move-result v1

    xor-int/2addr v0, v1

    return v0

    :cond_2
    add-int/lit8 v1, p0, 0x1

    invoke-static {v1}, LAmbgValPathExplosion;->isOdd(I)Z

    move-result v1

    xor-int/2addr v0, v1

    return v0
.end method

.method public static isOdd(I)Z
    .locals 2

    if-nez p0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const/4 v0, 0x1

    if-ne p0, v0, :cond_1

    return v0

    :cond_1
    if-lez p0, :cond_2

    add-int/lit8 v1, p0, -0x1

    invoke-static {v1}, LAmbgValPathExplosion;->isEven(I)Z

    move-result v1

    xor-int/2addr v0, v1

    return v0

    :cond_2
    add-int/lit8 v1, p0, 0x1

    invoke-static {v1}, LAmbgValPathExplosion;->isEven(I)Z

    move-result v1

    xor-int/2addr v0, v1

    return v0
.end method

# recursive n goind down and for each n value print n some text
.method public static recursionAndLoopMix(I)V
    .locals 2

    if-gtz p0, :cond_0

    return-void

    :cond_0
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "*"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    const/4 v0, 0x0

    :goto_0
    if-ge v0, p0, :cond_1

    add-int/lit8 v1, p0, -0x1

    invoke-static {v1}, LAmbgValPathExplosion;->recursionAndLoopMix(I)V

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method
