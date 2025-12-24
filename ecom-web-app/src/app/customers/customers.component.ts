import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
  customers : any;
  constructor(private http:HttpClient, private router: Router) {
  }
  ngOnInit() {
    this.http.get("http://localhost:9999/customer-service/api/customers").subscribe({
      next : (data)=>{
        this.customers=data;
      },
      error : (err)=>{}
    });
  }

  getOrders(c: any) {
    this.router.navigateByUrl("/orders/"+c.id);
  }
}
