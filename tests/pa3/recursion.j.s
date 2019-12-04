.data
.int_format:
	.asciz "%i\n"
.string_format:
	.asciz "%s\n"
.true:
	.asciz "true"
.false:
	.asciz "false"
	.text
	.global main
_Functions_0:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #60
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	mov v2, a2
	mov v3, #1
	cmp v2, v3
	movle v1, #1
	movgt v1, #0
	str v1, [fp, #-36]
	cmp v1, #1
	beq .L0
	ldr v2, [fp, #-32]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-40]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-40]
	bl _Functions_0
	mov v1, a1
	str v1, [fp, #-44]
	ldr v2, [fp, #-32]
	mov v3, #2
	sub v1, v2, v3
	str v1, [fp, #-48]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-48]
	bl _Functions_0
	mov v1, a1
	str v1, [fp, #-52]
	ldr v2, [fp, #-44]
	ldr v3, [fp, #-52]
	add v1, v2, v3
	str v1, [fp, #-56]
	mov a1, v1
	b _Functions_0exit
	b .L1
	.L0:
	mov v1, #1
	str v1, [fp, #-60]
	mov a1, v1
	b _Functions_0exit
	.L1:
_Functions_0exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Functions_1:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #56
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	mov v2, a2
	mov v3, #0
	cmp v2, v3
	moveq v1, #1
	movne v1, #0
	str v1, [fp, #-36]
	cmp v1, #1
	beq .L2
	ldr v2, [fp, #-32]
	mov v3, #1
	cmp v2, v3
	moveq v1, #1
	movne v1, #0
	str v1, [fp, #-40]
	ldr v2, [fp, #-32]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-44]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-44]
	bl _Functions_2
	mov v1, a1
	str v1, [fp, #-48]
	ldr v2, [fp, #-40]
	ldr v3, [fp, #-48]
	orr v1, v2, v3
	str v1, [fp, #-52]
	mov a1, v1
	b _Functions_1exit
	b .L3
	.L2:
	mov v1, #0
	str v1, [fp, #-56]
	mov a1, v1
	b _Functions_1exit
	.L3:
_Functions_1exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Functions_2:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #48
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	mov v2, a2
	mov v3, #0
	cmp v2, v3
	moveq v1, #1
	movne v1, #0
	str v1, [fp, #-36]
	cmp v1, #1
	beq .L4
	ldr v2, [fp, #-32]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-40]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-40]
	bl _Functions_1
	mov v1, a1
	str v1, [fp, #-44]
	mov a1, v1
	b _Functions_2exit
	b .L5
	.L4:
	mov v1, #1
	str v1, [fp, #-48]
	mov a1, v1
	b _Functions_2exit
	.L5:
_Functions_2exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Functions_3:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #72
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	str a3, [fp, #-36]
	ldr v2, [fp, #-32]
	mov v3, #0
	cmp v2, v3
	moveq v1, #1
	movne v1, #0
	str v1, [fp, #-40]
	cmp v1, #1
	beq .L6
	ldr v2, [fp, #-36]
	mov v3, #0
	cmp v2, v3
	moveq v1, #1
	movne v1, #0
	str v1, [fp, #-44]
	cmp v1, #1
	beq .L8
	ldr v2, [fp, #-32]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-48]
	ldr v2, [fp, #-36]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-52]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-32]
	ldr a3, [fp, #-52]
	bl _Functions_3
	mov v1, a1
	str v1, [fp, #-56]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-48]
	ldr a3, [fp, #-56]
	bl _Functions_3
	mov v1, a1
	str v1, [fp, #-60]
	mov a1, v1
	b _Functions_3exit
	b .L9
	.L8:
	ldr v2, [fp, #-32]
	mov v3, #1
	sub v1, v2, v3
	str v1, [fp, #-64]
	ldr a1, [fp, #-28]
	ldr a2, [fp, #-64]
	mov a3, #1
	bl _Functions_3
	mov v1, a1
	str v1, [fp, #-68]
	mov a1, v1
	b _Functions_3exit
	.L9:
	b .L7
	.L6:
	ldr v2, [fp, #-36]
	mov v3, #1
	add v1, v2, v3
	str v1, [fp, #-72]
	mov a1, v1
	b _Functions_3exit
	.L7:
_Functions_3exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #56
	str a1, [fp, #-28]
	ldr a1, [fp, #-56]
	mov a2, #15
	bl _Functions_0
	mov v1, a1
	str v1, [fp, #-32]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr a1, [fp, #-56]
	mov a2, #100
	bl _Functions_2
	mov v1, a1
	str v1, [fp, #-36]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-56]
	mov a2, #133
	bl _Functions_1
	mov v1, a1
	str v1, [fp, #-40]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-56]
	mov a2, #201
	bl _Functions_2
	mov v1, a1
	str v1, [fp, #-44]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-56]
	mov a2, #332
	bl _Functions_1
	mov v1, a1
	str v1, [fp, #-48]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-56]
	mov a2, #3
	mov a3, #4
	bl _Functions_3
	mov v1, a1
	str v1, [fp, #-52]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
mainexit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

