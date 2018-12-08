import { CustomerService, Customer } from './services';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-customer-list',
  template: `

  <table>
  <thead>
    <tr>
        <th>Filter</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>Hat ID:</td>
      <td>Hat Vorname:</td>
      <td>Hat Nachname:</td>
      <td>Geboren nach:</td>
      <td>Ist aktiv:</td>
    </tr>
    <tr>
      <td><input [(ngModel)]="filter.id" type="number" (ngModelChange)="filterList()"></td>
      <td><input [(ngModel)]="filter.firstName" type="text" (ngModelChange)="filterList()"></td>
      <td><input [(ngModel)]="filter.lastName" type="text" (ngModelChange)="filterList()"></td>
      <td><input [(ngModel)]="filter.birthDate" type="date" (ngModelChange)="filterList()"></td>
      <td><input [(ngModel)]="filter.active" type="checkbox" (ngModelChange)="filterList()"></td>
    </tr>
    </tbody>
  </table>

<br/>
<br/>
<br/>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Vorname</th>
          <th>Nachname</th>
          <th>Geburtsdatum</th>
          <th>Aktiv</th>
          <th><button (click)="addCustomer()">Kunden hinzufügen</button></th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let customer of filteredList">
          <td>{{ customer.id }}</td>
          <td>{{ customer.firstName }}</td>
          <td>{{ customer.lastName }}</td>
          <td>{{ customer.birthDate | date:'mediumDate' }}</td>
          <td>{{ customer.active }}</td>
          <td>
            <button (click)="editCustomer(customer)"><img src="assets/edit.gif"></button>
            <button (click)="deleteCustomer(customer)"><img src="assets/delete.gif"></button>
          </td>
        </tr>
      </tbody>
    </table>
  `,
  styles: [`
    * {
      margin: 0;
      padding: 0;
    }

    button {
      margin: 5px;
    }

    td {
      text-align: left;
    }

    td:first-of-type {
      text-align: right;
    }

    td, th {
      border: 1px solid black;
      padding: 5px;
    }

    th {
      text-align: center;
    }
  `]
})
export class CustomerListComponent implements OnInit {

  @Output() private add = new EventEmitter();
  @Output() private edit = new EventEmitter<number>();
  public customerList: Customer[];
  public filteredList: Customer[];
  public filter: Customer = new Customer();

  constructor(private customerService: CustomerService) { 
    this.filter.active = true;
  }

  filterList(){
    this.filteredList = this.customerList.filter((customerCandidate)=>{
      let idIsRelevant = true;
      if(this.filter.id){
        idIsRelevant = (customerCandidate.id == this.filter.id) ? true : false;
      }
      let firstNameIsRelevant = true;
      if(this.filter.firstName){
        firstNameIsRelevant = (customerCandidate.firstName.includes(this.filter.firstName)) ? true : false;
      }
      let lastNameIsRelevant = true;
      if(this.filter.lastName){
        lastNameIsRelevant = (customerCandidate.lastName.includes(this.filter.lastName)) ? true : false;
      }
      let birthDateIsRelevant = true;
      if(this.filter.birthDate){
        birthDateIsRelevant = (customerCandidate.birthDate > this.filter.birthDate ? true : false;
      }
      let activeStateIsRelevant = true;
      activeStateIsRelevant = (customerCandidate.active == this.filter.active) ? true : false;
      return (idIsRelevant && firstNameIsRelevant && lastNameIsRelevant && birthDateIsRelevant && activeStateIsRelevant);
    });
  }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.customerService.retrieveAll().then(
      (customerList) => {
        this.customerList = customerList;
        this.filterList();
      }
    );
  }

  private addCustomer() {
    this.add.emit();
  }

  private editCustomer(customer: Customer) {
    this.edit.emit(customer.id);
  }

  private deleteCustomer(customer: Customer) {
    if (confirm('Wollen Sie diesen Kunden wirklich löschen?')) {
      this.customerService.delete(customer.id).then(
        () => this.refresh()
      );
    }
  }
}
