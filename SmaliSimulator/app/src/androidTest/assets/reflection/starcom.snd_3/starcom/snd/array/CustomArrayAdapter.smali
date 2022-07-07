.class public Lstarcom/snd/array/CustomArrayAdapter;
.super Landroid/widget/ArrayAdapter;
.source "CustomArrayAdapter.java"

# interfaces
.implements Lstarcom/snd/listener/CallbackListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/ArrayAdapter",
        "<",
        "Lstarcom/snd/WebRadioChannel;",
        ">;",
        "Lstarcom/snd/listener/CallbackListener;"
    }
.end annotation


# instance fields
.field private final context:Landroid/app/Activity;


# direct methods
.method public constructor <init>(Landroid/app/Activity;)V
    .locals 1
    .param p1, "context"    # Landroid/app/Activity;

    .prologue
    .line 28
    const/4 v0, -0x1

    invoke-direct {p0, p1, v0}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I)V

    .line 29
    iput-object p1, p0, Lstarcom/snd/array/CustomArrayAdapter;->context:Landroid/app/Activity;

    .line 30
    return-void
.end method

.method private createCheckboxListener(I)Landroid/widget/CompoundButton$OnCheckedChangeListener;
    .locals 1
    .param p1, "pos"    # I

    .prologue
    .line 64
    new-instance v0, Lstarcom/snd/array/CustomArrayAdapter$1;

    invoke-direct {v0, p0, p1}, Lstarcom/snd/array/CustomArrayAdapter$1;-><init>(Lstarcom/snd/array/CustomArrayAdapter;I)V

    .line 75
    .local v0, "l":Landroid/widget/CompoundButton$OnCheckedChangeListener;
    return-object v0
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 12
    .param p1, "pos"    # I
    .param p2, "convertView"    # Landroid/view/View;
    .param p3, "parent"    # Landroid/view/ViewGroup;

    .prologue
    const v11, 0x7f02000c

    const/4 v10, 0x0

    .line 35
    iget-object v8, p0, Lstarcom/snd/array/CustomArrayAdapter;->context:Landroid/app/Activity;

    const-string/jumbo v9, "layout_inflater"

    .line 36
    invoke-virtual {v8, v9}, Landroid/app/Activity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/view/LayoutInflater;

    .line 37
    .local v4, "inflater":Landroid/view/LayoutInflater;
    invoke-virtual {p0, p1}, Lstarcom/snd/array/CustomArrayAdapter;->getItem(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/WebRadioChannel;

    .line 38
    .local v2, "curChannel":Lstarcom/snd/WebRadioChannel;
    invoke-virtual {v2}, Lstarcom/snd/WebRadioChannel;->getUrl()Ljava/lang/String;

    move-result-object v8

    const-string/jumbo v9, "###/*-! SEP /*!-###"

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_0

    .line 40
    const v8, 0x7f030005

    invoke-virtual {v4, v8, p3, v10}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v5

    .line 41
    .local v5, "rowView":Landroid/view/View;
    const v8, 0x7f020018

    invoke-virtual {v5, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/TextView;

    .line 42
    .local v7, "sepTxt":Landroid/widget/TextView;
    invoke-virtual {v2}, Lstarcom/snd/WebRadioChannel;->getName()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(I)V

    move-object v6, v5

    .line 59
    .end local v5    # "rowView":Landroid/view/View;
    .end local v7    # "sepTxt":Landroid/widget/TextView;
    .local v6, "rowView":Landroid/view/View;
    :goto_0
    return-object v6

    .line 45
    .end local v6    # "rowView":Landroid/view/View;
    :cond_0
    move-object v5, p2

    .line 46
    .restart local v5    # "rowView":Landroid/view/View;
    if-eqz v5, :cond_1

    invoke-virtual {v5, v11}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v8

    if-nez v8, :cond_2

    .line 48
    :cond_1
    const v8, 0x7f030002

    invoke-virtual {v4, v8, p3, v10}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v5

    .line 50
    :cond_2
    const v8, 0x7f020004

    invoke-virtual {v5, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 51
    .local v0, "channelTxt":Landroid/widget/TextView;
    invoke-virtual {v5, v11}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/ImageView;

    .line 52
    .local v3, "imageView":Landroid/widget/ImageView;
    const v8, 0x7f020009

    invoke-virtual {v5, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/CheckBox;

    .line 53
    .local v1, "checkbox":Landroid/widget/CheckBox;
    new-instance v8, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;

    iget-object v9, p0, Lstarcom/snd/array/CustomArrayAdapter;->context:Landroid/app/Activity;

    invoke-direct {v8, p1, v9, p0}, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;-><init>(ILandroid/app/Activity;Lstarcom/snd/listener/CallbackListener;)V

    invoke-virtual {v0, v8}, Landroid/widget/TextView;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 54
    invoke-virtual {v2}, Lstarcom/snd/WebRadioChannel;->getGenreIcon()I

    move-result v8

    invoke-virtual {v3, v8}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 55
    invoke-virtual {v2}, Lstarcom/snd/WebRadioChannel;->getName()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v0, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 56
    const/4 v8, 0x0

    invoke-virtual {v1, v8}, Landroid/widget/CheckBox;->setOnCheckedChangeListener(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V

    .line 57
    invoke-virtual {v2}, Lstarcom/snd/WebRadioChannel;->isSelected()Z

    move-result v8

    invoke-virtual {v1, v8}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 58
    invoke-direct {p0, p1}, Lstarcom/snd/array/CustomArrayAdapter;->createCheckboxListener(I)Landroid/widget/CompoundButton$OnCheckedChangeListener;

    move-result-object v8

    invoke-virtual {v1, v8}, Landroid/widget/CheckBox;->setOnCheckedChangeListener(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V

    move-object v6, v5

    .line 59
    .end local v5    # "rowView":Landroid/view/View;
    .restart local v6    # "rowView":Landroid/view/View;
    goto :goto_0
.end method

.method public onCallback()V
    .locals 6

    .prologue
    .line 114
    invoke-virtual {p0}, Lstarcom/snd/array/CustomArrayAdapter;->clear()V

    .line 115
    const/4 v2, 0x0

    const/16 v4, 0x2f

    const/4 v5, 0x0

    invoke-static {v4, v5, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lstarcom/snd/array/ChannelList;

    const/4 v2, 0x0

    const/16 v4, 0x30

    invoke-static {v4, v1, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/ArrayList;

    .line 116
    .local v0, "allChannels":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    invoke-virtual {p0, v0}, Lstarcom/snd/array/CustomArrayAdapter;->addAll(Ljava/util/Collection;)V

    .line 117
    return-void
.end method
