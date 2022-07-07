.class public final enum LFatEnum;
.super Ljava/lang/Enum;
.source "FatEnum.java"

# interfaces
.implements Ljava/lang/Readable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LFatEnum;",
        ">;",
        "Ljava/lang/Readable;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LFatEnum;

.field public static final enum STATE1:LFatEnum;

.field public static final enum STATE2:LFatEnum;

.field public static final enum STATE3:LFatEnum;


# instance fields
.field public a:I

.field public aa:[I

#TODO later
#.field public name:Ljava/lang/String;

#.field public ordinal:I


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 7
    new-instance v0, LFatEnum;

    const/4 v1, 0x0

    const-string v2, "STATE1"

    invoke-direct {v0, v2, v1}, LFatEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LFatEnum;->STATE1:LFatEnum;

    .line 8
    new-instance v0, LFatEnum;

    const/4 v2, 0x1

    const-string v3, "STATE2"

    invoke-direct {v0, v3, v2}, LFatEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LFatEnum;->STATE2:LFatEnum;

    .line 9
    new-instance v0, LFatEnum;

    const/4 v3, 0x2

    const-string v4, "STATE3"

    invoke-direct {v0, v4, v3}, LFatEnum;-><init>(Ljava/lang/String;I)V

    sput-object v0, LFatEnum;->STATE3:LFatEnum;

    .line 6
    const/4 v4, 0x3

    new-array v4, v4, [LFatEnum;

    sget-object v5, LFatEnum;->STATE1:LFatEnum;

    aput-object v5, v4, v1

    sget-object v1, LFatEnum;->STATE2:LFatEnum;

    aput-object v1, v4, v2

    aput-object v0, v4, v3

    sput-object v4, LFatEnum;->$VALUES:[LFatEnum;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 6
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 11
    const/16 p1, 0xc

    iput p1, p0, LFatEnum;->a:I

    .line 12
    const/4 p1, 0x2

    new-array p1, p1, [I

    fill-array-data p1, :array_0

    iput-object p1, p0, LFatEnum;->aa:[I

    .line 13
    const-string p1, "Fat Enum"

    iput-object p1, p0, LFatEnum;->name:Ljava/lang/String;

    .line 14
    const/16 p1, 0x14a

    iput p1, p0, LFatEnum;->ordinal:I

    return-void

    :array_0
    .array-data 4
        0xd
        0xe
    .end array-data
.end method

.method public static valueOf(Ljava/lang/String;)LFatEnum;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 6
    const-class v0, LFatEnum;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LFatEnum;

    return-object v0
.end method

.method public static values()[LFatEnum;
    .locals 1

    .line 6
    sget-object v0, LFatEnum;->$VALUES:[LFatEnum;

    invoke-virtual {v0}, [LFatEnum;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LFatEnum;

    return-object v0
.end method


# virtual methods
.method public methodInEnumTest1()V
    .locals 2

    .line 17
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "hi from fat enum"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 18
    return-void
.end method

.method public read(Ljava/nio/CharBuffer;)I
    .locals 1
    .param p1, "cb"    # Ljava/nio/CharBuffer;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 22
    const/16 v0, 0xc

    return v0
.end method
