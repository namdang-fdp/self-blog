'use client';
import { useState, useEffect } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { featuredPosts } from '../../constants/sample-data';
import Image from 'next/image';

export function FeaturedCarousel() {
    const [currentSlide, setCurrentSlide] = useState(0);
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentSlide((prev) => (prev + 1) % featuredPosts.length);
        }, 4000);
        return () => clearInterval(timer);
    }, []);

    const nextSlide = () => {
        setCurrentSlide((prev) => (prev + 1) % featuredPosts.length);
    };
    const prevSlide = () => {
        setCurrentSlide((prev) => (prev - 1) % featuredPosts.length);
    };

    return (
        <div className="relative w-full h-[500px] overflow-hidden rounded-xl">
            {featuredPosts.map((post, index) => (
                <div
                    key={post.id}
                    className={`absolute inset-0 transition-transform duration-500 ease-in-out ${
                        index === currentSlide
                            ? 'translate-x-0'
                            : index < currentSlide
                              ? '-translate-x-full'
                              : 'translate-x-full'
                    }`}
                >
                    <Card className="relative w-full h-full overflow-hidden border-0 shadow-2xl">
                        <Image
                            src={post.image || '/placeholder.svg'}
                            width={10}
                            height={10}
                            alt={post.title}
                            className="absolute inset-0 w-full h-full object-cover"
                        />
                        <div className="absolute inset-0 bg-black/70" />

                        <div className="relative z-20 flex flex-col justify-end h-full p-8">
                            <div className="mb-4">
                                <span className="inline-block px-3 py-1 text-sm font-medium bg-primary text-primary-foreground rounded-full mb-4">
                                    {post.category}
                                </span>
                                <h2 className="text-4xl font-bold mb-4 text-balance leading-tight text-white drop-shadow-lg">
                                    {post.title}
                                </h2>
                                <p className="text-lg mb-4 text-pretty max-w-2xl text-white/95 drop-shadow-md">
                                    {post.excerpt}
                                </p>
                                <div className="flex items-center space-x-4 text-sm text-white/90 drop-shadow-md">
                                    <span>{post.readTime}</span>
                                </div>
                            </div>
                        </div>
                    </Card>
                </div>
            ))}

            <Button
                variant="ghost"
                size="icon"
                className="absolute left-4 top-1/2 transform -translate-y-1/2 z-30 bg-black/30 hover:bg-black/50 text-white border-0 backdrop-blur-sm"
                onClick={prevSlide}
            >
                <ChevronLeft className="h-6 w-6" />
            </Button>
            <Button
                variant="ghost"
                size="icon"
                className="absolute right-4 top-1/2 transform -translate-y-1/2 z-30 bg-black/30 hover:bg-black/50 text-white border-0 backdrop-blur-sm"
                onClick={nextSlide}
            >
                <ChevronRight className="h-6 w-6" />
            </Button>

            <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 z-30 flex space-x-2">
                {featuredPosts.map((_, index) => (
                    <button
                        key={index}
                        className={`w-3 h-3 rounded-full transition-all ${index === currentSlide ? 'bg-white' : 'bg-white/50'}`}
                        onClick={() => setCurrentSlide(index)}
                    />
                ))}
            </div>
        </div>
    );
}
