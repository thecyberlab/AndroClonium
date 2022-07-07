.class public LComplexClass;
.super LSimpleClass;
.source "ComplexClass.java"

# interfaces
.implements LSimpleInterface;


# instance fields
.field public enumField:LSimpleEnum;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 14
    sget-object v0, LSimpleEnum;->STATE1:LSimpleEnum;

    invoke-direct {p0, v0}, LComplexClass;-><init>(LSimpleEnum;)V

    .line 15
    return-void
.end method

.method public constructor <init>(LSimpleEnum;)V
    .locals 0
    .param p1, "enumField"    # LSimpleEnum;

    .line 8
    invoke-direct {p0}, LSimpleClass;-><init>()V

    .line 9
    iput-object p1, p0, LComplexClass;->enumField:LSimpleEnum;

    .line 10
    return-void
.end method


# virtual methods
.method public simpleInterfaceTest()I
    .locals 1

    .line 19
    const/16 v0, 0xb

    return v0
.end method
