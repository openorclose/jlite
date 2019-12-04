.data
.int_format:
	.asciz "%i\n"
.string_format:
	.asciz "%s\n"
.true:
	.asciz "true"
.false:
	.asciz "false"
.string0:
	.asciz "loop"
	.text
	.global main
_Loop_0:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #32
	str a1, [fp, #-28]
	ldr a2, =.string0
	ldr a1, =.string_format
	bl printf(PLT)
	ldr a1, [fp, #-28]
	bl _Loop_0
	mov v1, a1
	str v1, [fp, #-32]
_Loop_0exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	mov a1, #0
	bl _Znwj(PLT)
	mov v1, a1
	str v1, [fp, #-32]
	str v1, [fp, #-40]
	mov a1, v1
	bl _Loop_0
	mov v1, a1
	str v1, [fp, #-36]
mainexit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

