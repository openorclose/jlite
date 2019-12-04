.data
.int_format:
	.asciz "%i\n"
.string_format:
	.asciz "%s\n"
.true:
	.asciz "true"
.false:
	.asciz "false"
.string1:
	.asciz "false part of if statement, should not be printed"
.string6:
	.asciz "counter less than 5!"
.string4:
	.asciz "should not be printed"
.string7:
	.asciz "test more than 4 argument functions"
.string0:
	.asciz "Hello world"
.string2:
	.asciz "true part of if statemenent. should be printed."
.string3:
	.asciz "should be printed"
.string5:
	.asciz "counter more than or equal 5!"
	.text
	.global main
_Functions_0:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #72
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	str a3, [fp, #-36]
	str a4, [fp, #-40]
	ldr v1, [fp, #12]
	str v1, [fp, #-44]
	ldr v1, [fp, #8]
	str v1, [fp, #-48]
	ldr v1, [fp, #4]
	str v1, [fp, #-52]
	ldr v1, [fp, #-32]
	mov v2, v1
	ldr v1, [fp, #-36]
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-56]
	ldr v1, [fp, #-56]
	mov v2, v1
	ldr v1, [fp, #-40]
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-60]
	ldr v1, [fp, #-60]
	mov v2, v1
	ldr v1, [fp, #-44]
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-64]
	ldr v1, [fp, #-64]
	mov v2, v1
	ldr v1, [fp, #-48]
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-68]
	ldr v1, [fp, #-68]
	mov v2, v1
	ldr v1, [fp, #-52]
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-72]
	ldr v1, [fp, #-72]
	mov a1, v1
	b _Functions_0exit
_Functions_0exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Functions_1:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	mov v1, #100
	str v1, [fp, #-40]
	ldr v1, [fp, #-40]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr v1, [fp, #-28]
	ldr v1, [v1, #0]
	str v1, [fp, #-36]
	ldr v1, [fp, #-36]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
_Functions_1exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Functions_2:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #32
	str a1, [fp, #-28]
	mov v1, #9999
	mov v2, v1
	ldr v1, [fp, #-28]
	str v2, [v1, #0]
	ldr v1, [fp, #-28]
	ldr v1, [v1, #0]
	str v1, [fp, #-32]
	ldr v1, [fp, #-32]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
_Functions_2exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #244
	str a1, [fp, #-28]
	mov v1, #1
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	mov v1, #1
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr v1, =.string0
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #2
	str v1, [fp, #-32]
	ldr v1, [fp, #-32]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	mov v1, #8
	str v1, [fp, #-36]
	ldr v1, [fp, #-36]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	mov v1, #700
	str v1, [fp, #-40]
	ldr v1, [fp, #-40]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	mov v1, #5
	neg v1, v1
	str v1, [fp, #-44]
	ldr v1, [fp, #-44]
	neg v1, v1
	str v1, [fp, #-48]
	ldr v1, [fp, #-48]
	neg v1, v1
	str v1, [fp, #-52]
	ldr v1, [fp, #-52]
	neg v1, v1
	str v1, [fp, #-56]
	ldr v1, [fp, #-56]
	neg v1, v1
	str v1, [fp, #-60]
	ldr v1, [fp, #-60]
	neg v1, v1
	str v1, [fp, #-64]
	ldr v1, [fp, #-64]
	neg v1, v1
	str v1, [fp, #-68]
	ldr v1, [fp, #-68]
	neg v1, v1
	str v1, [fp, #-72]
	ldr v1, [fp, #-72]
	neg v1, v1
	str v1, [fp, #-76]
	ldr v1, [fp, #-76]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	mov v1, #1
	eor v1, v1, #1
	str v1, [fp, #-80]
	ldr v1, [fp, #-80]
	eor v1, v1, #1
	str v1, [fp, #-84]
	ldr v1, [fp, #-84]
	eor v1, v1, #1
	str v1, [fp, #-88]
	ldr v1, [fp, #-88]
	eor v1, v1, #1
	str v1, [fp, #-92]
	ldr v1, [fp, #-92]
	eor v1, v1, #1
	str v1, [fp, #-96]
	ldr v1, [fp, #-96]
	eor v1, v1, #1
	str v1, [fp, #-100]
	ldr v1, [fp, #-100]
	eor v1, v1, #1
	str v1, [fp, #-104]
	ldr v1, [fp, #-104]
	eor v1, v1, #1
	str v1, [fp, #-108]
	ldr v1, [fp, #-108]
	eor v1, v1, #1
	str v1, [fp, #-112]
	ldr v1, [fp, #-112]
	eor v1, v1, #1
	str v1, [fp, #-116]
	ldr v1, [fp, #-116]
	eor v1, v1, #1
	str v1, [fp, #-120]
	ldr v1, [fp, #-120]
	eor v1, v1, #1
	str v1, [fp, #-124]
	ldr v1, [fp, #-124]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-128]
	ldr v1, [fp, #-128]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-132]
	ldr v1, [fp, #-132]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-136]
	ldr v1, [fp, #-136]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-140]
	ldr v1, [fp, #-140]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-144]
	ldr v1, [fp, #-144]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-148]
	ldr v1, [fp, #-148]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-152]
	ldr v1, [fp, #-152]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-156]
	ldr v1, [fp, #-156]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-160]
	ldr v1, [fp, #-160]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-164]
	ldr v1, [fp, #-164]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-168]
	ldr v1, [fp, #-168]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-172]
	ldr v1, [fp, #-172]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-176]
	ldr v1, [fp, #-176]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-180]
	ldr v1, [fp, #-180]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-184]
	ldr v1, [fp, #-184]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-188]
	ldr v1, [fp, #-188]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-192]
	ldr v1, [fp, #-192]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-196]
	ldr v1, [fp, #-196]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #0
	str v1, [fp, #-200]
	ldr v1, [fp, #-200]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	str v1, [fp, #-204]
	ldr v1, [fp, #-204]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov v1, #1
	cmp v1, #1
	beq .L0
	ldr v1, =.string1
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	b .L1
	.L0:
	ldr v1, =.string2
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	.L1:
	mov v1, #0
	cmp v1, #1
	beq .L2
	ldr v1, =.string3
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	b .L3
	.L2:
	ldr v1, =.string4
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	.L3:
	mov v1, #0
	str v1, [fp, #-244]
	.L4:
	ldr v1, [fp, #-244]
	mov v2, v1
	mov v1, #10
	mov v3, v1
	cmp v2, v3
	movlt v1, #1
	movge v1, #0
	str v1, [fp, #-208]
	ldr v1, [fp, #-208]
	cmp v1, #1
	beq .L5
	b .L6
	.L5:
	ldr v1, [fp, #-244]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr v1, [fp, #-244]
	mov v2, v1
	mov v1, #5
	mov v3, v1
	cmp v2, v3
	movlt v1, #1
	movge v1, #0
	str v1, [fp, #-212]
	ldr v1, [fp, #-212]
	cmp v1, #1
	beq .L7
	ldr v1, =.string5
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	b .L8
	.L7:
	ldr v1, =.string6
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	.L8:
	ldr v1, [fp, #-244]
	mov v2, v1
	mov v1, #1
	mov v3, v1
	add v1, v2, v3
	str v1, [fp, #-216]
	ldr v1, [fp, #-216]
	str v1, [fp, #-244]
	b .L4
	.L6:
	ldr v1, =.string7
	mov a2, v1
	ldr a1, =.string_format
	bl printf(PLT)
	mov a1, #4
	bl _Znwj(PLT)
	mov v1, a1
	str v1, [fp, #-220]
	ldr v1, [fp, #-220]
	str v1, [fp, #-240]
	mov v1, #1
	mov v2, v1
	ldr v1, [fp, #-240]
	str v2, [v1, #0]
	ldr v1, [fp, #-240]
	mov a1, v1
	mov v1, #1
	mov a2, v1
	mov v1, #2
	mov a3, v1
	mov v1, #3
	mov a4, v1
	mov v1, #4
	str v1, [sp, #-4]!
	mov v1, #5
	str v1, [sp, #-4]!
	mov v1, #6
	str v1, [sp, #-4]!
	bl _Functions_0
	add sp, sp, #12
	mov v1, a1
	str v1, [fp, #-224]
	ldr v1, [fp, #-224]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr v1, [fp, #-240]
	mov a1, v1
	mov v1, #10000
	mov a2, v1
	bl _Functions_1
	mov v1, a1
	str v1, [fp, #-228]
	ldr v1, [fp, #-240]
	mov a1, v1
	bl _Functions_2
	mov v1, a1
	str v1, [fp, #-232]
	mov v1, #0
	str v1, [fp, #-236]
	ldr v1, [fp, #-236]
	mov a1, v1
	b mainexit
mainexit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

