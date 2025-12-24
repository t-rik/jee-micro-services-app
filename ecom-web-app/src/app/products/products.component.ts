import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {
  products : any;
  constructor(private http:HttpClient) {
  }
  ngOnInit() {
    this.http.get("http://localhost:9999/inventory-service/api/products").subscribe({
      next : (data)=>{
        this.products=data;
      },
      error : (err)=>{}
    });
  }

}
