'use client';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { useLoginForm } from '@/hooks/use-login-form';
import { GalleryVerticalEnd } from 'lucide-react';
import Image from 'next/image';
import Link from 'next/link';

const LoginForm = () => {
    const { form } = useLoginForm();

    return (
        <Card className="w-full max-w-md mx-auto">
            <CardHeader>
                <CardTitle className="text-center text-2xl font-semibold">
                    Đăng nhập
                </CardTitle>
            </CardHeader>

            <CardContent>
                <Form {...form}>
                    <form className="flex flex-col gap-6">
                        <FormField
                            control={form.control}
                            name="email"
                            render={({ field }) => (
                                <FormItem className="grid gap-2">
                                    <FormLabel>Email</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            required
                                            placeholder="a@example.com"
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="password"
                            render={({ field }) => (
                                <FormItem className="grid gap-2">
                                    <FormLabel className="flex justify-between">
                                        Password
                                        <Link
                                            href="#"
                                            className="ml-auto text-sm text-red-600 underline-offset-4 hover:underline"
                                        >
                                            Quên mật khẩu?
                                        </Link>
                                    </FormLabel>
                                    <FormControl>
                                        <Input
                                            type="password"
                                            {...field}
                                            required
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <Button
                            type="submit"
                            className="w-full py-5 bg-blue-500 hover:bg-blue-600 cursor-pointer"
                        >
                            Đăng nhập
                        </Button>
                        <div className="relative text-center text-sm after:absolute after:inset-0 after:top-1/2 after:border-t after:border-border">
                            <span className="bg-background px-2 text-muted-foreground relative z-10">
                                Hoặc tiếp tục với
                            </span>
                        </div>

                        <Button
                            variant="outline"
                            className="w-full cursor-pointer"
                        >
                            <svg
                                className="mr-3 h-5 w-5 group-hover:scale-110 transition-transform"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    fill="#4285F4"
                                    d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"
                                />
                                <path
                                    fill="#34A853"
                                    d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"
                                />
                                <path
                                    fill="#FBBC05"
                                    d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"
                                />
                                <path
                                    fill="#EA4335"
                                    d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"
                                />
                            </svg>
                            Đăng nhập bằng Google
                        </Button>
                    </form>
                </Form>
            </CardContent>
        </Card>
    );
};

export default function LoginPage() {
    return (
        <div className="grid min-h-svh lg:grid-cols-2">
            <div className="flex flex-col gap-4 p-6 md:p-10">
                <div className="flex justify-center gap-2 md:justify-start">
                    <a href="#" className="flex items-center gap-2 font-medium">
                        <div className="bg-primary text-primary-foreground flex size-6 items-center justify-center rounded-md">
                            <GalleryVerticalEnd className="size-4" />
                        </div>
                        Dorriss Blog
                    </a>
                </div>
                <div className="flex flex-1 items-center justify-center">
                    <LoginForm />
                </div>
            </div>
            <div className="bg-muted relative hidden lg:block min-h-screen">
                <Image
                    src="/assets/blog-login.jpg"
                    alt="Image"
                    fill
                    className="object-cover dark:brightness-[0.2] dark:grayscale"
                />
            </div>
        </div>
    );
}
