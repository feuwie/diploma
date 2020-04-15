import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'format'
})
export class FormatPipe implements PipeTransform {
    transform(value: number): string {
        return String(value).replace(/(\d)(?=(\d\d\d)+([^\d]|$))/g, '$1 ');
    }
}
