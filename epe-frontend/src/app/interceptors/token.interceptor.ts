import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let authHeader = localStorage.getItem('JWT_TOKEN')

    if(authHeader)
    request = request.clone({
      setHeaders: {
        'Authorization': 'Bearer ' + authHeader
      }
    });
    return next.handle(request).pipe(
      map(event => {
        if (event instanceof HttpResponse) {
          console.log(event)
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