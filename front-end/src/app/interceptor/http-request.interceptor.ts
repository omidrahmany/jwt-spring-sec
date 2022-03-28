import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../servic/auth.service";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.authService.secureToken) {
      let requestClone = request.clone({
        headers: request.headers.append("authorization", this.authService.secureToken)
      });
      return next.handle(requestClone);
    } else {
      return next.handle(request);
    }
  }
}
