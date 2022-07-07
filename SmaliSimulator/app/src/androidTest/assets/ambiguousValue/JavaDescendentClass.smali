.class public LJavaDescendentClass;
.super Ljava/util/ArrayList;
.source "JavaDescendentClass.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 5
    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    return-void
.end method


.method public constructor <init>(I)V
    .locals 0

    .line 5
    invoke-direct {p0, p1}, Ljava/util/ArrayList;-><init>(I)V

    return-void
.end method



# virtual methods
.method public addToListAndSaySize(Ljava/lang/Object;)Ljava/lang/String;
    .locals 2

    invoke-virtual {p0, p1}, LJavaDescendentClass;->add(Ljava/lang/Object;)Z

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Array of size "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, LJavaDescendentClass;->size()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public wrapperOfAddToListAndSaySize(Ljava/lang/Object;)I
    .locals 1
    invoke-virtual {p0, p1}, LJavaDescendentClass;->addToListAndSaySize(Ljava/lang/Object;)Ljava/lang/String;

    invoke-virtual {p0}, LJavaDescendentClass;->size()I

    move-result v0

    return v0
.end method

.method public isIntNegative(I)Z
    .locals 2
    const/4 v0, 0x0

    if-ltz p1, :end

    const/4 v0, 0x1

    :end
    return v0
.end method
