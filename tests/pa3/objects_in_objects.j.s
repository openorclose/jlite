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
_Object_0:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #32
	str a1, [fp, #-28]
	mov v1, a1
	ldr v1, [v1, #0]
	str v1, [fp, #-32]
	mov a1, v1
	b _Object_0exit
_Object_0exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Object_1:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #28
	str a1, [fp, #-28]
	mov v2, #5
	ldr v1, [fp, #-28]
	str v2, [v1, #4]
	mov v2, #0
	ldr v1, [fp, #-28]
	str v2, [v1, #8]
_Object_1exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #84
	str a1, [fp, #-28]
	mov a1, #12
	bl _Znwj(PLT)
	mov v1, a1
	str v1, [fp, #-32]
	str v1, [fp, #-84]
	mov v2, #555
	ldr v1, [fp, #-84]
	str v2, [v1, #4]
	mov v2, #1
	ldr v1, [fp, #-84]
	str v2, [v1, #8]
	ldr v1, [fp, #-84]
	ldr v1, [v1, #4]
	str v1, [fp, #-36]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr v1, [fp, #-84]
	ldr v1, [v1, #8]
	str v1, [fp, #-40]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-84]
	bl _Object_1
	mov v1, a1
	str v1, [fp, #-44]
	ldr v1, [fp, #-84]
	ldr v1, [v1, #4]
	str v1, [fp, #-48]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr v1, [fp, #-84]
	ldr v1, [v1, #8]
	str v1, [fp, #-52]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
	mov a1, #12
	bl _Znwj(PLT)
	mov v1, a1
	str v1, [fp, #-56]
	mov v2, v1
	ldr v1, [fp, #-84]
	str v2, [v1, #0]
	ldr v1, [fp, #-84]
	ldr v1, [v1, #0]
	str v1, [fp, #-60]
	mov v2, #10
	ldr v1, [fp, #-60]
	str v2, [v1, #4]
	ldr v1, [fp, #-84]
	ldr v1, [v1, #0]
	str v1, [fp, #-64]
	mov v2, #1
	ldr v1, [fp, #-64]
	str v2, [v1, #8]
	ldr a1, [fp, #-84]
	bl _Object_0
	mov v1, a1
	str v1, [fp, #-68]
	ldr v1, [v1, #4]
	str v1, [fp, #-72]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr a1, [fp, #-84]
	bl _Object_0
	mov v1, a1
	str v1, [fp, #-76]
	ldr v1, [v1, #8]
	str v1, [fp, #-80]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
mainexit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

