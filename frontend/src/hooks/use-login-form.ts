'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import z from 'zod';

export const schema = z.object({
    email: z.string().min(1, { message: 'Invalid email input' }),
    password: z.string().nonempty({
        message: 'Password must not be empty',
    }),
});

export const useLoginForm = () => {
    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {},
    });

    return {
        form,
    };
};
