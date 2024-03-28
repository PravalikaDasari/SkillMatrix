import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-employee-layout',
  templateUrl: './employee-layout.component.html',
  styleUrl: './employee-layout.component.css'
})
export class EmployeeLayoutComponent {


  @Input() showDashboard: boolean = false;
  menuOptions: string[] = [];
  isArrowRotated1: boolean = false;
  isArrowRotated2: boolean = false;
  
  updateMenuOptions(options: string[]) {
    this.menuOptions = options;
  }
  
  toggleSubmenu(submenuClass: string) {
    const submenu = document.querySelector(`.${submenuClass}`);
    if (submenu) {
      submenu.classList.toggle('show');
    }
  }
  rotateArrow(arrowVariable: string) {
    (this as any)[arrowVariable] = !(this as any)[arrowVariable];
}

public empName:string|null='';
public user:any;
ngOnInit(): void {
  const user = localStorage.getItem("user");
  if (user) {
    this.user = JSON.parse(user);
    this.empName=this.user.userName
  }
}
}