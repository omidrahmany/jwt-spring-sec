import {Component, Input, OnInit,
  Output , EventEmitter} from '@angular/core';

@Component({
  selector: 'app-error-alert',
  templateUrl: './error-alert.component.html',
  styleUrls: ['./error-alert.component.css']
})
export class ErrorAlertComponent implements OnInit {

  @Input() errorMessage: string = '';
  @Output() close = new EventEmitter<void>();

  constructor() {
  }

  ngOnInit(): void {
  }

  onClose(){
    this.close.emit();
  }
}
