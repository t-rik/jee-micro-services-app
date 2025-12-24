import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orders : any;
  customerId! : number;
  constructor(private http:HttpClient, private router: Router, private route:ActivatedRoute) {
    this.customerId=route.snapshot.params['customerId'];
  }
  ngOnInit() {
    this.http.get("http://localhost:9999/order-service/orders/search/byCustomerId?customerId="+this.customerId).subscribe({
      next : (data)=>{
        this.orders=data;
      },
      error : (err)=>{}
    });
  }

  getOrderDetails(o: any) {
    this.router.navigateByUrl("/order-details/"+o.id);
  }
}
