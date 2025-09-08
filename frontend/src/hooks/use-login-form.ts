'use client';

import z from 'zod';

export const schema = z.object({
    email: z.string().min(1, { message: 'Invalid email input' }),
    password: z.string().nonempty({
        message: 'Password must not be empty',
    }),
});
