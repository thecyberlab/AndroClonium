.class public LClinitWithInstructionPayloadTest;
.super Landroid/widget/LinearLayout;
.source "NumberPicker.java"


.field private static final DIGIT_CHARACTERS:[C
.field private static final sTwoDigitFormatter:Ljava/lang/Object;
.field private static final DEFAULT_DIVIDER_TYPE:Ljava/lang/Object;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    goto/32 :goto_8

    nop

    :goto_0
    sput-object v0, LClinitWithInstructionPayloadTest;->DIGIT_CHARACTERS:[C

    nop

    goto/32 :goto_3

    nop

    :goto_1
    goto/32 :goto_9

    nop

    nop

    nop

    :goto_2
    new-instance v0, Ljava/lang/Object;

    nop

    nop

    nop

    nop

    goto/32 :goto_a

    nop

    :goto_3

    const-class v0, LClinitWithInstructionPayloadTest;

    const-string v1, "test"

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Class;

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    check-cast v0, Ljava/lang/reflect/Method;

    return-void

    nop

    nop

    :array_0
    .array-data 2
        0x30s
        0x31s
        0x32s
        0x33s
        0x34s
        0x35s
        0x36s
        0x37s
        0x38s
        0x39s
        0x660s
        0x661s
        0x662s
        0x663s
        0x664s
        0x665s
        0x666s
        0x667s
        0x668s
        0x669s
        0x6f0s
        0x6f1s
        0x6f2s
        0x6f3s
        0x6f4s
        0x6f5s
        0x6f6s
        0x6f7s
        0x6f8s
        0x6f9s
        0x966s
        0x967s
        0x968s
        0x969s
        0x96as
        0x96bs
        0x96cs
        0x96ds
        0x96es
        0x96fs
        0x9e6s
        0x9e7s
        0x9e8s
        0x9e9s
        0x9eas
        0x9ebs
        0x9ecs
        0x9eds
        0x9ees
        0x9efs
        0xce6s
        0xce7s
        0xce8s
        0xce9s
        0xceas
        0xcebs
        0xcecs
        0xceds
        0xcees
        0xcefs
        0x2ds
    .end array-data

    :goto_4
    goto/32 :goto_1

    nop

    :goto_5
    sput-object v0, LClinitWithInstructionPayloadTest;->DEFAULT_DIVIDER_TYPE:Ljava/lang/Object;

    nop

    nop

    nop

    nop

    nop

    .line 219
    goto/32 :goto_2

    nop

    :goto_6
    const/16 v0, 0x3d

    nop

    nop

    nop

    goto/32 :goto_c

    nop

    :goto_7
    const/4 v0, 0x0

    nop

    nop

    nop

    nop

    goto/32 :goto_5

    nop

    :goto_8
    goto/32 :goto_4

    nop

    nop

    nop

    .line 127
    :goto_9
    goto/32 :goto_7

    nop

    :goto_a
    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    goto/32 :goto_b

    nop

    :goto_b
    sput-object v0, LClinitWithInstructionPayloadTest;->sTwoDigitFormatter:Ljava/lang/Object;

    nop

    nop

    nop

    nop

    .line 2426
    goto/32 :goto_6

    nop

    :goto_c
    new-array v0, v0, [C

    nop

    nop

    nop

    fill-array-data v0, :array_0

    goto/32 :goto_0

    nop
.end method

.method public static test()V
    .locals 0

    return-void
.end method
