import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-order-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-details.component.html',
  styleUrl: './order-details.component.css'
})
export class OrderDetailsComponent implements OnInit {
  orderDetails : any;
  orderId! : number;
  constructor(private http:HttpClient, private router: Router, private route:ActivatedRoute) {
    this.orderId=route.snapshot.params['orderId'];
  }
  ngOnInit() {
    this.http.get("http://localhost:9999/order-service/fullOrder/"+this.orderId).subscribe({
      next : (data)=>{
        this.orderDetails=data;
      },
      error : (err)=>{}
    });
  }

  getTotal(orderDetails: any) {
    let total=0;
    for(let pi of orderDetails.productItems){
      total+=pi.amount;
    }
    return total;
  }
}
