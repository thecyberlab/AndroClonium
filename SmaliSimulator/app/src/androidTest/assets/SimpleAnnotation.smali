.class public interface abstract annotation LSimpleAnnotation;
.super Ljava/lang/Object;
.source "SimpleAnnotation.java"

# interfaces
.implements Ljava/lang/annotation/Annotation;


# annotations
.annotation system Ldalvik/annotation/AnnotationDefault;
    value = .subannotation LSimpleAnnotation;
        count = 0x1
        name = "test"
    .end subannotation
.end annotation


# virtual methods
.method public abstract count()I
.end method

.method public abstract name()Ljava/lang/String;
.end method
