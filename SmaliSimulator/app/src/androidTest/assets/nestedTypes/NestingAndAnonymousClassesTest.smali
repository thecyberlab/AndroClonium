.class public LnestedTypes/NestingAndAnonymousClassesTest;
.super Ljava/lang/Object;
.source "NestingAndAnonymousClassesTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()LnestedTypes/OuterClass$NestedClass;
    .locals 2

    .line 14
    new-instance v0, LnestedTypes/OuterClass;

    invoke-direct {v0}, LnestedTypes/OuterClass;-><init>()V

    .line 15
    .local v0, "oc":LnestedTypes/OuterClass;
    new-instance v1, LnestedTypes/OuterClass$NestedClass;

    invoke-static {v0}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-direct {v1, v0}, LnestedTypes/OuterClass$NestedClass;-><init>(LnestedTypes/OuterClass;)V

    return-object v1
.end method

.method public static test2()Ljava/util/ArrayList;
    .locals 2

    .line 20
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 21
    .local v0, "a":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/Integer;>;"
    const/4 v1, 0x1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 22
    const/4 v1, 0x3

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 23
    const/4 v1, 0x2

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 25
    new-instance v1, LnestedTypes/NestingAndAnonymousClassesTest$1;

    invoke-direct {v1}, LnestedTypes/NestingAndAnonymousClassesTest$1;-><init>()V

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->sort(Ljava/util/Comparator;)V

    .line 32
    return-object v0
.end method



