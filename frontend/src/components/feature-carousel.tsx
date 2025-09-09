import { useState, useEffect } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { featuredPosts } from '../../constants/sample-data';

export function FeaturedCarousel() {
    const [currentSlide, setCurrentSlide] = useState(0);
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentSlide((prev) => (prev + 1) % featuredPosts.length);
        }, 4000);
        return () => clearInterval(timer);
    }, []);
}
