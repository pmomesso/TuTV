namespace ApiCrawlerTuTV {
    partial class Form1 {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent() {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.seriesIdTo = new System.Windows.Forms.MaskedTextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.seriesIdFrom = new System.Windows.Forms.MaskedTextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.seriesIdTo);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.seriesIdFrom);
            this.groupBox1.Controls.Add(this.button1);
            this.groupBox1.Location = new System.Drawing.Point(33, 13);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(522, 281);
            this.groupBox1.TabIndex = 5;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Preparado para crawlear";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(68, 110);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(163, 25);
            this.label2.TabIndex = 9;
            this.label2.Text = "Crawlear hasta id";
            // 
            // seriesIdTo
            // 
            this.seriesIdTo.Location = new System.Drawing.Point(273, 110);
            this.seriesIdTo.Mask = "9999999";
            this.seriesIdTo.Name = "seriesIdTo";
            this.seriesIdTo.Size = new System.Drawing.Size(176, 29);
            this.seriesIdTo.TabIndex = 8;
            this.seriesIdTo.Text = "66732";
            this.seriesIdTo.ValidatingType = typeof(int);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(68, 50);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(169, 25);
            this.label1.TabIndex = 7;
            this.label1.Text = "Crawlear desde id";
            // 
            // seriesIdFrom
            // 
            this.seriesIdFrom.Location = new System.Drawing.Point(273, 50);
            this.seriesIdFrom.Mask = "9999999";
            this.seriesIdFrom.Name = "seriesIdFrom";
            this.seriesIdFrom.Size = new System.Drawing.Size(176, 29);
            this.seriesIdFrom.TabIndex = 6;
            this.seriesIdFrom.Text = "66732";
            this.seriesIdFrom.ValidatingType = typeof(int);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(68, 175);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(381, 74);
            this.button1.TabIndex = 5;
            this.button1.Text = "¡Crawlear!";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.Button1_Click_1);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 24F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(588, 311);
            this.Controls.Add(this.groupBox1);
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "TuTV Api Crawler";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.MaskedTextBox seriesIdTo;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.MaskedTextBox seriesIdFrom;
        private System.Windows.Forms.Button button1;
    }
}

