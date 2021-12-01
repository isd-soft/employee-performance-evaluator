import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

      let authHeader = localStorage.getItem('JWT_TOKEN')

      if (authHeader)
        request = request.clone({
          setHeaders: {
            'Authorization': 'Bearer ' + authHeader,
            'Cache-Control': 'no-cache, no-store, must-revalidate, post-check=0, pre-check=0',
            'Pragma': 'no-cache',
            'Expires': 'Sat, 01 Jan 2000 00:00:00 GMT',
            'If-Modified-Since': '0'
          }
        });
      return next.handle(request).pipe(
        map(event => {
          if (event instanceof HttpResponse) {3
          }
          return event;
        }),
        catchError(error => {
          if (error.status === 403) {
            window.alert('Forbidden !');
          }
          return throwError(error);
        })
      );
  }
}
