.class public Lstarcom/snd/dialog/TextDialog;
.super Lstarcom/snd/listener/DialogFragmentWithListener;
.source "TextDialog.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# static fields
.field static aboutTxt:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 19
    invoke-direct {p0}, Lstarcom/snd/listener/DialogFragmentWithListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 41
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f020015

    if-ne v0, v1, :cond_0

    .line 43
    const-class v0, Lstarcom/snd/dialog/TextDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Selected: aboutOk"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 44
    invoke-virtual {p0}, Lstarcom/snd/dialog/TextDialog;->dismiss()V

    .line 46
    :cond_0
    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 9
    .param p1, "inflater"    # Landroid/view/LayoutInflater;
    .param p2, "container"    # Landroid/view/ViewGroup;
    .param p3, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 24
    const v3, 0x7f030007

    invoke-virtual {p1, v3, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v2

    .line 25
    .local v2, "view":Landroid/view/View;
    const v3, 0x7f020015

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    .line 26
    .local v1, "okButton":Landroid/widget/Button;
    const v3, 0x7f020008

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    .line 27
    .local v0, "listText":Landroid/widget/EditText;
    const/4 v3, 0x0

    invoke-virtual {v0, v3}, Landroid/widget/EditText;->setFocusable(Z)V

    .line 28
    sget-object v3, Lstarcom/snd/dialog/TextDialog;->aboutTxt:Ljava/lang/String;

    if-nez v3, :cond_0

    .line 30
    invoke-virtual {p0}, Lstarcom/snd/dialog/TextDialog;->getActivity()Landroid/app/Activity;

    move-result-object v3

    const/high16 v4, 0x7f040000

    const/4 v5, 0x2

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v3, v5, v7

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    const/4 v8, 0x1

    aput-object v6, v5, v8

    const/16 v7, 0x3a

    const/4 v8, 0x0

    invoke-static {v7, v8, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    sput-object v3, Lstarcom/snd/dialog/TextDialog;->aboutTxt:Ljava/lang/String;

    .line 32
    :cond_0
    sget-object v3, Lstarcom/snd/dialog/TextDialog;->aboutTxt:Ljava/lang/String;

    invoke-virtual {v0, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 33
    invoke-virtual {v1, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 34
    invoke-virtual {p0}, Lstarcom/snd/dialog/TextDialog;->getDialog()Landroid/app/Dialog;

    move-result-object v3

    const/high16 v4, 0x7f050000

    invoke-virtual {v3, v4}, Landroid/app/Dialog;->setTitle(I)V

    .line 35
    return-object v2
.end method
