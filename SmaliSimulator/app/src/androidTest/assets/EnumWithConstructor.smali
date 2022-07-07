.class public final enum LEnumWithConstructor;
.super Ljava/lang/Enum;
.source "EnumWithConstructor.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumWithConstructor;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumWithConstructor;

.field public static final enum State1:LEnumWithConstructor;

.field public static final enum State2:LEnumWithConstructor;

.field public static final enum State3:LEnumWithConstructor;


# instance fields
.field public name:Ljava/lang/String;

.field public number:I


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .line 5
    new-instance v0, LEnumWithConstructor;

    const/4 v1, 0x0

    const-string v2, "State1"

    const/16 v3, 0xa

    const-string v4, "N1"

    invoke-direct {v0, v2, v1, v3, v4}, LEnumWithConstructor;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LEnumWithConstructor;->State1:LEnumWithConstructor;

    .line 6
    new-instance v0, LEnumWithConstructor;

    const/4 v2, 0x1

    const-string v3, "State2"

    const/16 v4, 0x14

    const-string v5, "N2"

    invoke-direct {v0, v3, v2, v4, v5}, LEnumWithConstructor;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LEnumWithConstructor;->State2:LEnumWithConstructor;

    .line 7
    new-instance v0, LEnumWithConstructor;

    const/4 v3, 0x2

    const-string v4, "State3"

    const/16 v5, 0x1e

    const-string v6, "N3"

    invoke-direct {v0, v4, v3, v5, v6}, LEnumWithConstructor;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LEnumWithConstructor;->State3:LEnumWithConstructor;

    .line 3
    const/4 v4, 0x3

    new-array v4, v4, [LEnumWithConstructor;

    sget-object v5, LEnumWithConstructor;->State1:LEnumWithConstructor;

    aput-object v5, v4, v1

    sget-object v1, LEnumWithConstructor;->State2:LEnumWithConstructor;

    aput-object v1, v4, v2

    aput-object v0, v4, v3

    sput-object v4, LEnumWithConstructor;->$VALUES:[LEnumWithConstructor;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;IILjava/lang/String;)V
    .locals 0
    .param p3, "i"    # I
    .param p4, "s"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .line 9
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 10
    iput p3, p0, LEnumWithConstructor;->number:I

    .line 11
    iput-object p4, p0, LEnumWithConstructor;->name:Ljava/lang/String;

    .line 13
    return-void
.end method

.method public static greet()Ljava/lang/String;
    .locals 2

    .line 22
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Hello from "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-class v1, LEnumWithConstructor;

    invoke-virtual {v1}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static valueOf(Ljava/lang/String;)LEnumWithConstructor;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 3
    const-class v0, LEnumWithConstructor;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumWithConstructor;

    return-object v0
.end method

.method public static values()[LEnumWithConstructor;
    .locals 1

    .line 3
    sget-object v0, LEnumWithConstructor;->$VALUES:[LEnumWithConstructor;

    invoke-virtual {v0}, [LEnumWithConstructor;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumWithConstructor;

    return-object v0
.end method


# virtual methods
.method public getSignature()Ljava/lang/String;
    .locals 2

    .line 18
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, LEnumWithConstructor;->name:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, LEnumWithConstructor;->number:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
